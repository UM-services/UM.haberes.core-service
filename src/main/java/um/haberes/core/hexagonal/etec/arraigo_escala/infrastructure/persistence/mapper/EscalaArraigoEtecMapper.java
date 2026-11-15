package um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.etec.arraigo_escala.domain.model.EscalaArraigoEtec;
import um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.persistence.entity.EscalaArraigoEtecEntity;

@Component
public class EscalaArraigoEtecMapper {

    public EscalaArraigoEtec toDomain(EscalaArraigoEtecEntity entity) {
        return EscalaArraigoEtec.builder()
                .escalaArraigoEtecId(entity.getEtecArraigoEscalaId())
                .aniosMinimos(entity.getAniosMinimos())
                .porcentaje(entity.getPorcentaje())
                .build();
    }

    public EscalaArraigoEtecEntity toEntity(EscalaArraigoEtec domain) {
        return new EscalaArraigoEtecEntity(domain.getEscalaArraigoEtecId(), domain.getAniosMinimos(),
                domain.getPorcentaje());
    }
}
