package um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;

public interface SaveAllLetrasUseCase {

    List<Letra> saveAllLetras(List<Letra> letras);
}
