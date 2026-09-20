package um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.web.dto.AcreditacionRequest;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.web.dto.AcreditacionResponse;

@Component
public class AcreditacionDtoMapper {

    public Acreditacion toDomain(AcreditacionRequest request) {
        if (request == null) {
            return null;
        }
        return Acreditacion.builder()
                .anho(request.getAnho())
                .mes(request.getMes())
                .acreditado(request.getAcreditado())
                .limiteNovedades(request.getLimiteNovedades())
                .fechaContable(request.getFechaContable())
                .ordenContable(request.getOrdenContable())
                .sueldosOriginal(request.getSueldosOriginal())
                .sueldosAjustados(request.getSueldosAjustados())
                .contribucionesPatronales(request.getContribucionesPatronales())
                .build();
    }

    public AcreditacionResponse toResponse(Acreditacion domain) {
        if (domain == null) {
            return null;
        }
        return AcreditacionResponse.builder()
                .acreditacionId(domain.getAcreditacionId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .acreditado(domain.getAcreditado())
                .limiteNovedades(domain.getLimiteNovedades())
                .fechaContable(domain.getFechaContable())
                .ordenContable(domain.getOrdenContable())
                .sueldosOriginal(domain.getSueldosOriginal())
                .sueldosAjustados(domain.getSueldosAjustados())
                .contribucionesPatronales(domain.getContribucionesPatronales())
                .build();
    }
}
