package um.haberes.core.hexagonal.personas.persona_facultad.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.personas.persona_facultad.application.exception.PersonaFacultadException;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.model.PersonaFacultad;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.out.PersonaFacultadRepository;

@ExtendWith(MockitoExtension.class)
class CreatePersonaFacultadUseCaseImplTest {

    private static final Long LEGAJO = 100L;

    private static final Integer FACULTAD = 1;

    @Mock
    private PersonaFacultadRepository repository;

    @InjectMocks
    private CreatePersonaFacultadUseCaseImpl useCase;

    @Test
    void creaLaFacultadHabilitadaCuandoNoExiste() {
        PersonaFacultad nueva = PersonaFacultad.builder().legajoId(LEGAJO).facultadId(FACULTAD).build();
        PersonaFacultad guardada = PersonaFacultad.builder()
                .personaFacultadId(7L).legajoId(LEGAJO).facultadId(FACULTAD).build();
        when(repository.findByLegajoIdAndFacultadId(LEGAJO, FACULTAD)).thenReturn(Optional.empty());
        when(repository.save(nueva)).thenReturn(guardada);

        PersonaFacultad resultado = useCase.createPersonaFacultad(nueva);

        assertThat(resultado.getPersonaFacultadId()).isEqualTo(7L);
        verify(repository).save(nueva);
    }

    @Test
    void rechazaDuplicadosSinGuardar() {
        PersonaFacultad existente = PersonaFacultad.builder().legajoId(LEGAJO).facultadId(FACULTAD).build();
        when(repository.findByLegajoIdAndFacultadId(LEGAJO, FACULTAD)).thenReturn(Optional.of(existente));

        assertThatThrownBy(() -> useCase.createPersonaFacultad(existente))
                .isInstanceOf(PersonaFacultadException.class)
                .hasMessageContaining("already exists");
        verify(repository, never()).save(any());
    }

    @Test
    void exigeLegajoYFacultad() {
        assertThatThrownBy(() -> useCase.createPersonaFacultad(
                PersonaFacultad.builder().facultadId(FACULTAD).build()))
                .isInstanceOf(PersonaFacultadException.class);
        assertThatThrownBy(() -> useCase.createPersonaFacultad(
                PersonaFacultad.builder().legajoId(LEGAJO).build()))
                .isInstanceOf(PersonaFacultadException.class);
        assertThatThrownBy(() -> useCase.createPersonaFacultad(null))
                .isInstanceOf(PersonaFacultadException.class);
        verify(repository, never()).save(any());
    }
}
