package um.haberes.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.personas.persona.application.service.PersonaService;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.model.AntiguedadEntity;
import um.haberes.core.repository.JpaAntiguedadRepository;
import um.haberes.core.service.view.AntiguedadPeriodoService;

@ExtendWith(MockitoExtension.class)
class AntiguedadServiceTest {

    @Mock
    private JpaAntiguedadRepository repository;

    @Mock
    private AntiguedadPeriodoService antiguedadPeriodoService;

    @Mock
    private PersonaService personaService;

    @InjectMocks
    private AntiguedadService service;

    private Persona personaConAltaEtec(int anho, int mes) {
        return Persona.builder()
                .legajoId(100L)
                .altaEtec(OffsetDateTime.of(anho, mes, 1, 0, 0, 0, 0, ZoneOffset.UTC))
                .build();
    }

    @Test
    void calculaMesesEtecDesdeElAltaHastaElPeriodo() {
        when(personaService.findByLegajoId(100L)).thenReturn(personaConAltaEtec(2025, 3));
        when(repository.findByLegajoIdAndAnhoAndMes(100L, 2026, 5)).thenReturn(Optional.empty());
        when(repository.save(any(AntiguedadEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.calculate(100L, 2026, 5);

        ArgumentCaptor<AntiguedadEntity> captor = ArgumentCaptor.forClass(AntiguedadEntity.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getMesesEtec()).isEqualTo(14);
        assertThat(captor.getValue().getMesesDocentes()).isZero();
        assertThat(captor.getValue().getMesesAdministrativos()).isZero();
    }

    @Test
    void dejaMesesEtecEnCeroCuandoElPeriodoEsAnteriorAlAlta() {
        when(personaService.findByLegajoId(100L)).thenReturn(personaConAltaEtec(2026, 3));
        when(repository.findByLegajoIdAndAnhoAndMes(100L, 2026, 1)).thenReturn(Optional.empty());
        when(repository.save(any(AntiguedadEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.calculate(100L, 2026, 1);

        ArgumentCaptor<AntiguedadEntity> captor = ArgumentCaptor.forClass(AntiguedadEntity.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getMesesEtec()).isZero();
    }

    @Test
    void calculaMesesAdministrativosDesdeElAltaAdministrativa() {
        Persona persona = Persona.builder()
                .legajoId(100L)
                .altaAdministrativa(OffsetDateTime.of(2024, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC))
                .ajusteAdministrativo(2)
                .build();
        when(personaService.findByLegajoId(100L)).thenReturn(persona);
        when(repository.findByLegajoIdAndAnhoAndMes(100L, 2025, 1)).thenReturn(Optional.empty());
        when(repository.save(any(AntiguedadEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.calculate(100L, 2025, 1);

        ArgumentCaptor<AntiguedadEntity> captor = ArgumentCaptor.forClass(AntiguedadEntity.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getMesesAdministrativos()).isEqualTo(14);
    }

    @Test
    void dejaMesesEtecEnCeroCuandoLaPersonaNoTieneAltaEtec() {
        when(personaService.findByLegajoId(100L)).thenReturn(Persona.builder().legajoId(100L).build());
        when(repository.findByLegajoIdAndAnhoAndMes(100L, 2026, 1)).thenReturn(Optional.empty());
        when(repository.save(any(AntiguedadEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.calculate(100L, 2026, 1);

        ArgumentCaptor<AntiguedadEntity> captor = ArgumentCaptor.forClass(AntiguedadEntity.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getMesesEtec()).isZero();
    }
}
