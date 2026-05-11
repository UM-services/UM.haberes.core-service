package um.haberes.core.hexagonal.geografica.domain.ports.in;

import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import java.util.Optional;

public interface UpdateGeograficaUseCase {
    Geografica update(Integer id, Geografica geografica);
}
