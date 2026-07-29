package um.haberes.core.hexagonal.geografica.domain.ports.in;

import um.haberes.core.hexagonal.geografica.domain.model.Geografica;

public interface CreateGeograficaUseCase {
    Geografica createGeografica(Geografica geografica);
}
