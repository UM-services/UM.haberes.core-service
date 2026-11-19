package um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.etec.arraigo_escala.domain.model.EscalaArraigoEtec;
import um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.web.dto.EscalaArraigoEtecRequest;
import um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.web.dto.EscalaArraigoEtecResponse;

@Component
public class EscalaArraigoEtecDtoMapper {

    public EscalaArraigoEtec toDomain(EscalaArraigoEtecRequest request) {
        return EscalaArraigoEtec.builder()
                .aniosMinimos(request.getAniosMinimos())
                .porcentaje(request.getPorcentaje())
                .build();
    }

    public EscalaArraigoEtecResponse toResponse(EscalaArraigoEtec domain) {
        return new EscalaArraigoEtecResponse(domain.getEscalaArraigoEtecId(),
                domain.getAniosMinimos(), domain.getPorcentaje());
    }
}
