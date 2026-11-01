package um.haberes.core.hexagonal.personas.persona.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.personas.persona.application.exception.PersonaException;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.model.PersonaPatch;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@ExtendWith(MockitoExtension.class)
class PartialUpdatePersonaUseCaseImplTest {

    private static final Long LEGAJO_ID = 100L;

    @Mock
    private PersonaRepository personaRepository;

    @InjectMocks
    private PartialUpdatePersonaUseCaseImpl useCase;

    private Persona persona() {
        return Persona.builder()
                .legajoId(LEGAJO_ID)
                .documento(new BigDecimal("30111222"))
                .apellido("Original")
                .nombre("Ana")
                .cuil("27301112224")
                .obraSocial(5L)
                .build();
    }

    @Test
    void aplicaSoloLosCamposInformadosYConservaElResto() {
        when(personaRepository.findByLegajoId(LEGAJO_ID)).thenReturn(Optional.of(persona()));
        when(personaRepository.update(eq(LEGAJO_ID), any(Persona.class)))
                .thenAnswer(invocation -> Optional.of(invocation.getArgument(1)));

        Persona resultado = useCase.partialUpdatePersona(
                LEGAJO_ID, PersonaPatch.builder().apellido("Corregido").build());

        assertThat(resultado.getApellido()).isEqualTo("Corregido");
        assertThat(resultado.getNombre()).isEqualTo("Ana");
        assertThat(resultado.getCuil()).isEqualTo("27301112224");
        assertThat(resultado.getDocumento()).isEqualByComparingTo("30111222");
        assertThat(resultado.getObraSocial()).isEqualTo(5L);
    }

    @Test
    void interpretaCeroComoBorrarEnLosNumericosAnulables() {
        when(personaRepository.findByLegajoId(LEGAJO_ID)).thenReturn(Optional.of(persona()));
        when(personaRepository.update(eq(LEGAJO_ID), any(Persona.class)))
                .thenAnswer(invocation -> Optional.of(invocation.getArgument(1)));

        Persona resultado = useCase.partialUpdatePersona(LEGAJO_ID,
                PersonaPatch.builder().obraSocial(0L).build());

        assertThat(resultado.getObraSocial()).isNull();
    }

    @Test
    void fallaSiElLegajoNoExiste() {
        when(personaRepository.findByLegajoId(LEGAJO_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.partialUpdatePersona(LEGAJO_ID, new PersonaPatch()))
                .isInstanceOf(PersonaException.class);

        verify(personaRepository, never()).update(eq(LEGAJO_ID), any(Persona.class));
    }

    @Test
    void fallaSiElRepositorioNoPudoActualizar() {
        when(personaRepository.findByLegajoId(LEGAJO_ID)).thenReturn(Optional.of(persona()));
        when(personaRepository.update(eq(LEGAJO_ID), any(Persona.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.partialUpdatePersona(
                LEGAJO_ID, PersonaPatch.builder().nombre("Nuevo").build()))
                .isInstanceOf(PersonaException.class);
    }
}
