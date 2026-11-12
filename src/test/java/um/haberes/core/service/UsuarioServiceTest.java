package um.haberes.core.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.apache.commons.codec.digest.DigestUtils;
import um.haberes.core.exception.UsuarioException;
import um.haberes.core.model.UsuarioEntity;
import um.haberes.core.model.dto.CambiarClaveRequest;
import um.haberes.core.repository.JpaUsuarioRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    private static final Long LEGAJO_ID = 100L;
    private static final OffsetDateTime LAST_LOG = OffsetDateTime.parse("2026-01-15T12:00:00+00:00");

    @Mock
    private JpaUsuarioRepository repository;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioEntity storedUsuario(String hashedPassword) {
        return UsuarioEntity.builder()
                .legajoId(LEGAJO_ID)
                .password(hashedPassword)
                .lastLog(LAST_LOG)
                .build(42L)
                .usuarioId(9L)
                .facultadId(1)
                .administrador((byte) 0)
                .build();
    }

    private CambiarClaveRequest.CambiarClaveRequestBuilder baseRequest() {
        return CambiarClaveRequest.builder()
                .legajoId(LEGAJO_ID)
                .currentPassword("claveVieja123")
                .newPassword("nuevaClaveSegura123")
                .reClaveNueva("nuevaClaveSegura123");
    }

    @Test
    void cambiarClave_whenNewPasswordIsEmpty_throwsException() {
        assertThatThrownBy(() -> usuarioService.cambiarClave(
                baseRequest().newPassword("").reClaveNueva("").build()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ERROR: Falta CLAVE . . .");
    }

    @Test
    void cambiarClave_whenPasswordsDoNotMatch_throwsException() {
        assertThatThrownBy(() -> usuarioService.cambiarClave(
                baseRequest().newPassword("nuevaClave1").reClaveNueva("otraClave2").build()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ERROR: Claves NO Coinciden");
    }

    @Test
    void cambiarClave_whenCurrentPasswordIsEmpty_throwsException() {
        assertThatThrownBy(() -> usuarioService.cambiarClave(
                baseRequest().currentPassword("  ").build()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ERROR: Falta Clave Anterior");
    }

    @Test
    void cambiarClave_whenUserNotFound_throwsException() {
        when(repository.findByLegajoId(LEGAJO_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.cambiarClave(baseRequest().build()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ERROR: Usuario NO Encontrado");
        verify(repository, never()).save(any());
    }

    @Test
    void cambiarClave_whenLegajoIdIsNull_throwsException() {
        assertThatThrownBy(() -> usuarioService.cambiarClave(baseRequest().legajoId(null).build()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ERROR: Usuario NO Encontrado");
        verify(repository, never()).save(any());
    }

    @Test
    void cambiarClave_whenCurrentPasswordIncorrect_throwsException() {
        when(repository.findByLegajoId(LEGAJO_ID)).thenReturn(
                Optional.of(storedUsuario(DigestUtils.sha256Hex("otraClave"))));

        assertThatThrownBy(() -> usuarioService.cambiarClave(baseRequest().build()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ERROR: Usuario NO Autenticado");
        verify(repository, never()).save(any());
    }

    @Test
    void cambiarClave_whenUserHasEmptyPassword_throwsException() {
        when(repository.findByLegajoId(LEGAJO_ID)).thenReturn(
                Optional.of(storedUsuario("")));

        assertThatThrownBy(() -> usuarioService.cambiarClave(baseRequest().build()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ERROR: Usuario NO Autenticado");
        verify(repository, never()).save(any());
    }

    @Test
    void cambiarClave_successfulUpdate_preservesAuditColumns() {
        when(repository.findByLegajoId(LEGAJO_ID)).thenReturn(
                Optional.of(storedUsuario(DigestUtils.sha256Hex("claveVieja123"))));

        usuarioService.cambiarClave(baseRequest().build());

        ArgumentCaptor<UsuarioEntity> captor = ArgumentCaptor.forClass(UsuarioEntity.class);
        verify(repository).save(captor.capture());
        UsuarioEntity saved = captor.getValue();
        assertThat(saved.getLegajoId()).isEqualTo(LEGAJO_ID);
        assertThat(saved.getPassword()).isEqualTo(DigestUtils.sha256Hex("nuevaClaveSegura123"));
        assertThat(saved.getLastLog()).isEqualTo(LAST_LOG);
        assertThat(saved.getBuild()).isEqualTo(42L);
        assertThat(saved.getUsuarioId()).isEqualTo(9L);
        assertThat(saved.getFacultadId()).isEqualTo(1);
        assertThat(saved.getAdministrador()).isEqualTo((byte) 0);
    }

    @Test
    void updateLastLog_preservesAdministradorFlag() {
        UsuarioEntity admin = storedUsuario("hash").toBuilder()
                .facultadId(null)
                .administrador((byte) 1)
                .build();
        when(repository.findByLegajoId(LEGAJO_ID)).thenReturn(Optional.of(admin));
        when(repository.save(any(UsuarioEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioEntity updated = usuarioService.updateLastLog(LEGAJO_ID, 43L);

        assertThat(updated.getAdministrador()).isEqualTo((byte) 1);
        assertThat(updated.getFacultadId()).isNull();
        assertThat(updated.getBuild()).isEqualTo(43L);
        assertThat(updated.getPassword()).isEqualTo("hash");
    }

    @Test
    void setPassword_preservesAdministradorFlag() {
        UsuarioEntity admin = storedUsuario("hash").toBuilder()
                .facultadId(null)
                .administrador((byte) 1)
                .build();
        when(repository.findByLegajoId(LEGAJO_ID)).thenReturn(Optional.of(admin));

        usuarioService.setPassword(UsuarioEntity.builder()
                .legajoId(LEGAJO_ID)
                .password("nuevaClave")
                .build(44L)
                .build());

        ArgumentCaptor<UsuarioEntity> captor = ArgumentCaptor.forClass(UsuarioEntity.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getAdministrador()).isEqualTo((byte) 1);
        assertThat(captor.getValue().getPassword()).isEqualTo(DigestUtils.sha256Hex("nuevaClave"));
    }

    @Test
    void setAdministrador_whenValueIsInvalid_throwsException() {
        assertThatThrownBy(() -> usuarioService.setAdministrador(LEGAJO_ID, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ERROR: Valor de administrador invalido");
        verify(repository, never()).save(any());
    }

    @Test
    void setAdministrador_whenUserNotFound_throwsException() {
        when(repository.findByLegajoId(LEGAJO_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.setAdministrador(LEGAJO_ID, 1))
                .isInstanceOf(UsuarioException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void setAdministrador_whenUserHasFacultad_throwsException() {
        when(repository.findByLegajoId(LEGAJO_ID)).thenReturn(Optional.of(storedUsuario("hash")));

        assertThatThrownBy(() -> usuarioService.setAdministrador(LEGAJO_ID, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ERROR: Usuario con facultad asignada NO puede ser administrador");
        verify(repository, never()).save(any());
    }

    @Test
    void setAdministrador_whenRevokingLastAdmin_throwsException() {
        UsuarioEntity admin = storedUsuario("hash").toBuilder()
                .facultadId(null)
                .administrador((byte) 1)
                .build();
        when(repository.findByLegajoId(LEGAJO_ID)).thenReturn(Optional.of(admin));
        when(repository.countByAdministrador((byte) 1)).thenReturn(1L);

        assertThatThrownBy(() -> usuarioService.setAdministrador(LEGAJO_ID, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ERROR: Debe quedar al menos un administrador");
        verify(repository, never()).save(any());
    }

    @Test
    void setAdministrador_whenEnabling_persistsFlag() {
        UsuarioEntity usuario = storedUsuario("hash").toBuilder().facultadId(null).build();
        when(repository.findByLegajoId(LEGAJO_ID)).thenReturn(Optional.of(usuario));
        when(repository.save(any(UsuarioEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioEntity saved = usuarioService.setAdministrador(LEGAJO_ID, 1);

        assertThat(saved.getAdministrador()).isEqualTo((byte) 1);
        assertThat(saved.getPassword()).isEqualTo("hash");
    }
}
