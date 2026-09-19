package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.model.LegajoContabilidad;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.web.dto.LegajoContabilidadRequest;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.web.dto.LegajoContabilidadResponse;

@Component
public class LegajoContabilidadDtoMapper {

    public LegajoContabilidad toDomain(LegajoContabilidadRequest request) {
        if (request == null) {
            return null;
        }
        return LegajoContabilidad.builder()
                .legajoId(request.getLegajoId())
                .anho(request.getAnho())
                .mes(request.getMes())
                .diferencia(request.getDiferencia())
                .remunerativo(request.getRemunerativo())
                .noRemunerativo(request.getNoRemunerativo())
                .build();
    }

    public LegajoContabilidadResponse toResponse(LegajoContabilidad domain) {
        if (domain == null) {
            return null;
        }
        return LegajoContabilidadResponse.builder()
                .legajoContabilidadId(domain.getLegajoContabilidadId())
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .diferencia(domain.getDiferencia())
                .remunerativo(domain.getRemunerativo())
                .noRemunerativo(domain.getNoRemunerativo())
                .build();
    }
}
