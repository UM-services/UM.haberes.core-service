package um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;

public interface GetLetrasByPeriodoUseCase {

    List<Letra> getLetrasByPeriodo(Integer anho, Integer mes, Integer limit);
}
