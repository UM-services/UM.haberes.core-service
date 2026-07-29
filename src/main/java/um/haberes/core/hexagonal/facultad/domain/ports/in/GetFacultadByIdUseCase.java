package um.haberes.core.hexagonal.facultad.domain.ports.in;

import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import java.util.Optional;

public interface GetFacultadByIdUseCase {
    Optional<Facultad> getById(Integer id);
}
