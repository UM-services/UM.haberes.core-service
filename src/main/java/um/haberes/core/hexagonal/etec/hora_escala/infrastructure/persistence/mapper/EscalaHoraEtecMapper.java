package um.haberes.core.hexagonal.etec.hora_escala.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;
import um.haberes.core.hexagonal.etec.hora_escala.infrastructure.persistence.entity.EscalaHoraEtecEntity;

@Component
public class EscalaHoraEtecMapper {

    public EscalaHoraEtec toDomain(EscalaHoraEtecEntity entity) {
        return EscalaHoraEtec.builder()
                .escalaHoraEtecId(entity.getEtecHoraEscalaId())
                .controlEtecId(entity.getControlEtecId())
                .horasDesde(entity.getHorasDesde())
                .horasHasta(entity.getHorasHasta())
                .montoFijo(entity.getMontoFijo())
                .valorHora(entity.getValorHora())
                .build();
    }

    public EscalaHoraEtecEntity toEntity(EscalaHoraEtec domain) {
        return new EscalaHoraEtecEntity(domain.getEscalaHoraEtecId(), domain.getControlEtecId(),
                domain.getHorasDesde(), domain.getHorasHasta(), domain.getMontoFijo(),
                domain.getValorHora());
    }
}
