package um.haberes.core.hexagonal.liquidaciones.afip_concepto_sueldo.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.afip_concepto_sueldo.domain.model.AfipConceptoSueldo;
import um.haberes.core.model.AfipConceptoSueldoEntity;

/**
 * El slice de afipConceptoSueldo todavía no está migrado a hexagonal: la entidad
 * de persistencia vive en {@code um.haberes.core.model}. Este mapper existe sólo
 * para poder exponer el concepto anidado en los DTOs de los slices migrados.
 */
@Component
public class AfipConceptoSueldoMapper {

    public AfipConceptoSueldo toDomain(AfipConceptoSueldoEntity entity) {
        if (entity == null) {
            return null;
        }
        return AfipConceptoSueldo.builder()
                .afipConceptoSueldoId(entity.getAfipConceptoSueldoId())
                .descripcion(entity.getDescripcion())
                .asignado(entity.getAsignado())
                .build();
    }
}
