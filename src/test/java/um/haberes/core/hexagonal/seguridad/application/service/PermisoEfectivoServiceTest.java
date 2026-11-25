package um.haberes.core.hexagonal.seguridad.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import um.haberes.core.hexagonal.seguridad.application.exception.SeguridadNotFoundException;
import um.haberes.core.hexagonal.seguridad.domain.model.Permiso;
import um.haberes.core.hexagonal.seguridad.domain.model.PermisoEfectivo;
import um.haberes.core.hexagonal.seguridad.domain.model.Rol;
import um.haberes.core.hexagonal.seguridad.domain.model.RolPermiso;
import um.haberes.core.hexagonal.seguridad.domain.model.UsuarioPermiso;
import um.haberes.core.hexagonal.seguridad.domain.model.UsuarioRol;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.PermisoRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.RolPermisoRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.RolRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.UsuarioPermisoRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.UsuarioRolRepository;
import um.haberes.core.model.UsuarioEntity;
import um.haberes.core.repository.JpaUsuarioRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PermisoEfectivoServiceTest {

    private static final Long LEGAJO = 725L;

    @Mock private JpaUsuarioRepository usuarioRepository;
    @Mock private PermisoRepository permisoRepository;
    @Mock private RolRepository rolRepository;
    @Mock private RolPermisoRepository rolPermisoRepository;
    @Mock private UsuarioRolRepository usuarioRolRepository;
    @Mock private UsuarioPermisoRepository usuarioPermisoRepository;

    @InjectMocks private PermisoEfectivoService service;

    private final Rol rolAdmin = Rol.builder().rolId(10L).nombre("ADMIN_HABERES")
            .aplicacion("HABERES").activo((byte) 1).build();
    private final Rol rolOperador = Rol.builder().rolId(20L).nombre("OPERADOR_NOVEDADES")
            .aplicacion("HABERES").activo((byte) 1).build();
    private final Permiso permisoNovedades = Permiso.builder().permisoId(1L)
            .clave("novedades.anotador.gestionar").modulo("novedades").aplicacion("HABERES")
            .activo((byte) 1).build();
    private final Permiso permisoLiquidacion = Permiso.builder().permisoId(2L)
            .clave("liquidacion.general.procesar").modulo("liquidacion").aplicacion("HABERES")
            .activo((byte) 1).build();

    private void catalogo() {
        when(permisoRepository.findAllByAplicacionAndActivo("HABERES", (byte) 1))
                .thenReturn(List.of(permisoNovedades, permisoLiquidacion));
        when(rolRepository.findAllByAplicacion("HABERES")).thenReturn(List.of(rolAdmin, rolOperador));
    }

    @Test
    void administradorRecibeTodasLasClaves() {
        when(usuarioRepository.findByLegajoId(LEGAJO)).thenReturn(Optional.of(
                UsuarioEntity.builder().legajoId(LEGAJO).administrador((byte) 1).build()));
        catalogo();
        when(usuarioRolRepository.findAllByLegajoId(LEGAJO)).thenReturn(List.of());
        when(usuarioPermisoRepository.findAllByLegajoId(LEGAJO)).thenReturn(List.of());
        when(rolPermisoRepository.findAllByRolId(10L)).thenReturn(List.of(
                RolPermiso.builder().rolId(10L).permisoId(1L).build(),
                RolPermiso.builder().rolId(10L).permisoId(2L).build()));

        PermisoEfectivo efectivo = service.obtener(LEGAJO);

        assertThat(efectivo.isAdministrador()).isTrue();
        assertThat(efectivo.getPermisos()).containsExactly(
                "liquidacion.general.procesar", "novedades.anotador.gestionar");
    }

    @Test
    void unOverrideNoPuedeRevocarleUnaClaveAlAdministrador() {
        when(usuarioRepository.findByLegajoId(LEGAJO)).thenReturn(Optional.of(
                UsuarioEntity.builder().legajoId(LEGAJO).administrador((byte) 1).build()));
        catalogo();
        when(usuarioRolRepository.findAllByLegajoId(LEGAJO)).thenReturn(List.of());
        when(usuarioPermisoRepository.findAllByLegajoId(LEGAJO)).thenReturn(List.of(
                UsuarioPermiso.builder().legajoId(LEGAJO).permisoId(1L).otorgado((byte) 0).build()));
        when(rolPermisoRepository.findAllByRolId(10L)).thenReturn(List.of(
                RolPermiso.builder().rolId(10L).permisoId(1L).build(),
                RolPermiso.builder().rolId(10L).permisoId(2L).build()));

        PermisoEfectivo efectivo = service.obtener(LEGAJO);

        assertThat(efectivo.getPermisos()).contains(
                "novedades.anotador.gestionar", "liquidacion.general.procesar");
    }

    @Test
    void heredaDelRolYAplicaOverrides() {
        when(usuarioRepository.findByLegajoId(LEGAJO)).thenReturn(Optional.of(
                UsuarioEntity.builder().legajoId(LEGAJO).administrador((byte) 0).build()));
        catalogo();
        when(usuarioRolRepository.findAllByLegajoId(LEGAJO)).thenReturn(List.of(
                UsuarioRol.builder().legajoId(LEGAJO).rolId(20L).build()));
        when(rolPermisoRepository.findAllByRolId(20L)).thenReturn(List.of(
                RolPermiso.builder().rolId(20L).permisoId(1L).build()));
        when(usuarioPermisoRepository.findAllByLegajoId(LEGAJO)).thenReturn(List.of(
                UsuarioPermiso.builder().legajoId(LEGAJO).permisoId(1L).otorgado((byte) 0).build(),
                UsuarioPermiso.builder().legajoId(LEGAJO).permisoId(2L).otorgado((byte) 1).build()));

        PermisoEfectivo efectivo = service.obtener(LEGAJO);

        assertThat(efectivo.isAdministrador()).isFalse();
        assertThat(efectivo.getPermisos()).containsExactly("liquidacion.general.procesar");
    }

    @Test
    void unRolInactivoNoAportaClaves() {
        Rol inactivo = Rol.builder().rolId(30L).nombre("INACTIVO").aplicacion("HABERES")
                .activo((byte) 0).build();
        when(usuarioRepository.findByLegajoId(LEGAJO)).thenReturn(Optional.of(
                UsuarioEntity.builder().legajoId(LEGAJO).administrador((byte) 0).build()));
        when(permisoRepository.findAllByAplicacionAndActivo("HABERES", (byte) 1))
                .thenReturn(List.of(permisoNovedades));
        when(rolRepository.findAllByAplicacion("HABERES")).thenReturn(List.of(inactivo));
        when(usuarioRolRepository.findAllByLegajoId(LEGAJO)).thenReturn(List.of(
                UsuarioRol.builder().legajoId(LEGAJO).rolId(30L).build()));
        when(usuarioPermisoRepository.findAllByLegajoId(LEGAJO)).thenReturn(List.of());

        PermisoEfectivo efectivo = service.obtener(LEGAJO);

        assertThat(efectivo.getPermisos()).isEmpty();
    }

    @Test
    void usuarioInexistenteLanzaNotFound() {
        when(usuarioRepository.findByLegajoId(LEGAJO)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.obtener(LEGAJO))
                .isInstanceOf(SeguridadNotFoundException.class);
    }
}
