package um.haberes.core.hexagonal.personas.persona_facultad.application.usecases;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.out.PersonaFacultadRepository;

@ExtendWith(MockitoExtension.class)
class DeletePersonaFacultadByUniqueUseCaseImplTest {

    @Mock
    private PersonaFacultadRepository repository;

    @InjectMocks
    private DeletePersonaFacultadByUniqueUseCaseImpl useCase;

    @Test
    void borraPorLegajoYFacultad() {
        useCase.deletePersonaFacultadByUnique(100L, 1);

        verify(repository).deleteByLegajoIdAndFacultadId(100L, 1);
    }
}
