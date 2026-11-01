package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.model.LegajoContabilidad;

public interface FindLegajoContabilidadesDiferenciaByPeriodoUseCase {

    List<LegajoContabilidad> findLegajoContabilidadesDiferenciaByPeriodo(Integer anho, Integer mes);
}
