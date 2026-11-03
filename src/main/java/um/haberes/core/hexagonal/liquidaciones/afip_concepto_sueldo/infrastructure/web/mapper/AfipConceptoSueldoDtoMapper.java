package um.haberes.core.hexagonal.liquidaciones.afip_concepto_sueldo.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.afip_concepto_sueldo.domain.model.AfipConceptoSueldo;
import um.haberes.core.hexagonal.liquidaciones.afip_concepto_sueldo.infrastructure.web.dto.AfipConceptoSueldoResponse;

@Component
public class AfipConceptoSueldoDtoMapper {

    public AfipConceptoSueldoResponse toResponse(AfipConceptoSueldo domain) {
        if (domain == null) {
            return null;
        }
        return AfipConceptoSueldoResponse.builder()
                .afipConceptoSueldoId(domain.getAfipConceptoSueldoId())
                .descripcion(domain.getDescripcion())
                .asignado(domain.getAsignado())
                .build();
    }
}
