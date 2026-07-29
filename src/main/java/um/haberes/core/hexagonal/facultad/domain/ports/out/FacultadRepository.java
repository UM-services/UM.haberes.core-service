package um.haberes.core.hexagonal.facultad.domain.ports.out;

import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import java.util.List;
import java.util.Optional;

public interface FacultadRepository {
    Facultad create(Facultad facultad);
    Optional<Facultad> findById(Integer id);
    List<Facultad> findAll();
    List<Facultad> findAllIn(List<Integer> ids);
    Optional<Facultad> update(Integer id, Facultad facultad);
    boolean deleteById(Integer id);
}
