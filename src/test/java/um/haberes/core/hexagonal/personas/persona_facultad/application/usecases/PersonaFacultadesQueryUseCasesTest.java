package um.haberes.core.hexagonal.personas.persona_facultad.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.personas.persona_facultad.domain.model.PersonaFacultad;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.out.PersonaFacultadRepository;

@ExtendWith(MockitoExtension.class)
class PersonaFacultadesQueryUseCasesTest {

    private static final Long LEGAJO = 100L;

    private static final Integer FACULTAD = 1;

    @Mock
    private PersonaFacultadRepository repository;

    @InjectMocks
    private GetPersonaFacultadesByLegajoUseCaseImpl byLegajo;

    @InjectMocks
    private GetPersonaFacultadesByFacultadUseCaseImpl byFacultad;

    @Test
    void buscaLasHabilitadasDelLegajo() {
        List<PersonaFacultad> esperadas = List.of(
                PersonaFacultad.builder().legajoId(LEGAJO).facultadId(FACULTAD).build());
        when(repository.findAllByLegajoId(LEGAJO)).thenReturn(esperadas);

        assertThat(byLegajo.getPersonaFacultadesByLegajo(LEGAJO)).isEqualTo(esperadas);
        verify(repository).findAllByLegajoId(LEGAJO);
    }

    @Test
    void buscaLasHabilitadasDeLaFacultad() {
        List<PersonaFacultad> esperadas = List.of(
                PersonaFacultad.builder().legajoId(LEGAJO).facultadId(FACULTAD).build());
        when(repository.findAllByFacultadId(FACULTAD)).thenReturn(esperadas);

        assertThat(byFacultad.getPersonaFacultadesByFacultad(FACULTAD)).isEqualTo(esperadas);
        verify(repository).findAllByFacultadId(FACULTAD);
    }
}
