package um.haberes.core.hexagonal.etec.presentismo.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;
import um.haberes.core.hexagonal.etec.presentismo.infrastructure.persistence.entity.EtecPresentismoEntity;

@Component
public class EtecPresentismoMapper {

    public EtecPresentismoEntity toEntity(EtecPresentismo domain) {
        if (domain == null) {
            return null;
        }
        EtecPresentismoEntity entity = new EtecPresentismoEntity();
        entity.setPresentismoId(domain.getPresentismoId());
        entity.setAsignacionCargoId(domain.getAsignacionCargoId());
        entity.setAnho(domain.getAnho());
        entity.setMes(domain.getMes());
        entity.setMotivo(domain.getMotivo());
        return entity;
    }

    public EtecPresentismo toDomain(EtecPresentismoEntity entity) {
        if (entity == null) {
            return null;
        }
        EtecPresentismo.EtecPresentismoBuilder builder = EtecPresentismo.builder()
                .presentismoId(entity.getPresentismoId())
                .asignacionCargoId(entity.getAsignacionCargoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .motivo(entity.getMotivo());
        if (entity.getAsignacion() != null) {
            builder.legajoId(entity.getAsignacion().getLegajoId())
                    .categoriaId(entity.getAsignacion().getCategoriaId())
                    .dependenciaId(entity.getAsignacion().getDependenciaId());
            if (entity.getAsignacion().getCategoria() != null) {
                builder.categoriaNombre(entity.getAsignacion().getCategoria().getNombre());
            }
            if (entity.getAsignacion().getDependencia() != null) {
                builder.dependenciaNombre(entity.getAsignacion().getDependencia().getNombre());
            }
            if (entity.getAsignacion().getPersona() != null) {
                builder.personaApellido(entity.getAsignacion().getPersona().getApellido());
                builder.personaNombre(entity.getAsignacion().getPersona().getNombre());
            }
        }
        return builder.build();
    }
}
