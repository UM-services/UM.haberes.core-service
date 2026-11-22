package um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;
import um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.web.dto.EtecPresentismoHorasRequest;
import um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.web.dto.EtecPresentismoHorasResponse;
import um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.web.dto.EtecPresentismoHorasUpdateRequest;

@Component
public class EtecPresentismoHorasDtoMapper {

    public EtecPresentismoHoras toDomain(EtecPresentismoHorasRequest request) {
        if (request == null) {
            return null;
        }
        return EtecPresentismoHoras.builder()
                .legajoId(request.getLegajoId())
                .anho(request.getAnho())
                .mes(request.getMes())
                .motivo(request.getMotivo())
                .build();
    }

    public EtecPresentismoHoras toDomain(EtecPresentismoHorasUpdateRequest request) {
        if (request == null) {
            return null;
        }
        return EtecPresentismoHoras.builder()
                .motivo(request.getMotivo())
                .build();
    }

    public EtecPresentismoHorasResponse toResponse(EtecPresentismoHoras domain) {
        if (domain == null) {
            return null;
        }
        return EtecPresentismoHorasResponse.builder()
                .presentismoHorasId(domain.getPresentismoHorasId())
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .motivo(domain.getMotivo())
                .personaApellido(domain.getPersonaApellido())
                .personaNombre(domain.getPersonaNombre())
                .build();
    }
}
