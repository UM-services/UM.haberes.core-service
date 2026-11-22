package um.haberes.core.hexagonal.etec.presentismo.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;
import um.haberes.core.hexagonal.etec.presentismo.infrastructure.web.dto.EtecPresentismoRequest;
import um.haberes.core.hexagonal.etec.presentismo.infrastructure.web.dto.EtecPresentismoResponse;
import um.haberes.core.hexagonal.etec.presentismo.infrastructure.web.dto.EtecPresentismoUpdateRequest;

@Component
public class EtecPresentismoDtoMapper {

    public EtecPresentismo toDomain(EtecPresentismoRequest request) {
        if (request == null) {
            return null;
        }
        return EtecPresentismo.builder()
                .asignacionCargoId(request.getAsignacionCargoId())
                .anho(request.getAnho())
                .mes(request.getMes())
                .motivo(request.getMotivo())
                .build();
    }

    public EtecPresentismo toDomain(EtecPresentismoUpdateRequest request) {
        if (request == null) {
            return null;
        }
        return EtecPresentismo.builder()
                .motivo(request.getMotivo())
                .build();
    }

    public EtecPresentismoResponse toResponse(EtecPresentismo domain) {
        if (domain == null) {
            return null;
        }
        return EtecPresentismoResponse.builder()
                .presentismoId(domain.getPresentismoId())
                .asignacionCargoId(domain.getAsignacionCargoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .motivo(domain.getMotivo())
                .legajoId(domain.getLegajoId())
                .categoriaId(domain.getCategoriaId())
                .dependenciaId(domain.getDependenciaId())
                .categoriaNombre(domain.getCategoriaNombre())
                .dependenciaNombre(domain.getDependenciaNombre())
                .personaApellido(domain.getPersonaApellido())
                .personaNombre(domain.getPersonaNombre())
                .build();
    }
}
