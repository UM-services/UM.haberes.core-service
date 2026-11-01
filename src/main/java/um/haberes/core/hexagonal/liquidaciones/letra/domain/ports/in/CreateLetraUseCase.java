package um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;

public interface CreateLetraUseCase {

    Letra createLetra(Letra letra);
}
