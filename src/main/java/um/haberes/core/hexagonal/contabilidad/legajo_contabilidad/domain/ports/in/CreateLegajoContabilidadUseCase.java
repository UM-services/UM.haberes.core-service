package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.in;

import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.model.LegajoContabilidad;

public interface CreateLegajoContabilidadUseCase {

    LegajoContabilidad createLegajoContabilidad(LegajoContabilidad legajoContabilidad);
}
