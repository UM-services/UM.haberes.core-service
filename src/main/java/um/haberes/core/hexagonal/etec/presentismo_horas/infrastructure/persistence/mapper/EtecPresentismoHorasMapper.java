package um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;
import um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.persistence.entity.EtecPresentismoHorasEntity;

@Component
public class EtecPresentismoHorasMapper {

    public EtecPresentismoHorasEntity toEntity(EtecPresentismoHoras domain) {
        if (domain == null) {
            return null;
        }
        EtecPresentismoHorasEntity entity = new EtecPresentismoHorasEntity();
        entity.setPresentismoHorasId(domain.getPresentismoHorasId());
        entity.setLegajoId(domain.getLegajoId());
        entity.setAnho(domain.getAnho());
        entity.setMes(domain.getMes());
        entity.setMotivo(domain.getMotivo());
        return entity;
    }

    public EtecPresentismoHoras toDomain(EtecPresentismoHorasEntity entity) {
        if (entity == null) {
            return null;
        }
        EtecPresentismoHoras.EtecPresentismoHorasBuilder builder = EtecPresentismoHoras.builder()
                .presentismoHorasId(entity.getPresentismoHorasId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .motivo(entity.getMotivo());
        if (entity.getPersona() != null) {
            builder.personaApellido(entity.getPersona().getApellido());
            builder.personaNombre(entity.getPersona().getNombre());
        }
        return builder.build();
    }
}
