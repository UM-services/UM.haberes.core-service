package um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.web.dto.NovedadRequest;
import um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.web.dto.NovedadResponse;

@Component
public class NovedadDtoMapper {

    public Novedad toDomain(NovedadRequest request) {
        if (request == null) {
            return null;
        }
        Novedad.NovedadBuilder builder = Novedad.builder()
                .legajoId(request.getLegajoId())
                .anho(request.getAnho())
                .mes(request.getMes())
                .codigoId(request.getCodigoId())
                .dependenciaId(request.getDependenciaId())
                .observaciones(request.getObservaciones())
                .importado(request.getImportado())
                .novedadUploadId(request.getNovedadUploadId());
        if (request.getImporte() != null) {
            builder.importe(request.getImporte());
        }
        if (request.getValue() != null) {
            builder.value(request.getValue());
        }
        return builder.build();
    }

    public NovedadResponse toResponse(Novedad domain) {
        if (domain == null) {
            return null;
        }
        return NovedadResponse.builder()
                .novedadId(domain.getNovedadId())
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .codigoId(domain.getCodigoId())
                .dependenciaId(domain.getDependenciaId())
                .importe(domain.getImporte())
                .value(domain.getValue())
                .observaciones(domain.getObservaciones())
                .importado(domain.getImportado())
                .novedadUploadId(domain.getNovedadUploadId())
                .build();
    }
}
