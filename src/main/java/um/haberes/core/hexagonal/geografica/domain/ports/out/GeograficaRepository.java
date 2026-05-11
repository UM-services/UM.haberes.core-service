package um.haberes.core.hexagonal.geografica.domain.ports.out;

import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import java.util.List;
import java.util.Optional;

public interface GeograficaRepository {
    Geografica create(Geografica geografica);
    Geografica findById(Integer id);
    List<Geografica> findAll();
    List<Geografica> findAllIn(List<Integer> ids);
    Geografica update(Integer id, Geografica geografica);
    boolean deleteById(Integer id);
}
