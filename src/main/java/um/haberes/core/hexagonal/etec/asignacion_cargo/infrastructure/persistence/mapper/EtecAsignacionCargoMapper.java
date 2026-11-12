package um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;
import um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.persistence.entity.EtecAsignacionCargoEntity;

@Component
@RequiredArgsConstructor
public class EtecAsignacionCargoMapper {

    public EtecAsignacionCargoEntity toEntity(EtecAsignacionCargo domain) {
        if (domain == null) {
            return null;
        }
        EtecAsignacionCargoEntity entity = new EtecAsignacionCargoEntity();
        entity.setAsignacionCargoId(domain.getAsignacionCargoId());
        entity.setLegajoId(domain.getLegajoId());
        entity.setCategoriaId(domain.getCategoriaId());
        entity.setDependenciaId(domain.getDependenciaId());
        entity.setFechaAlta(domain.getFechaAlta());
        entity.setFechaBaja(domain.getFechaBaja());
        entity.setObservaciones(domain.getObservaciones());
        return entity;
    }

    public EtecAsignacionCargo toDomain(EtecAsignacionCargoEntity entity) {
        if (entity == null) {
            return null;
        }
        EtecAsignacionCargo.EtecAsignacionCargoBuilder builder = EtecAsignacionCargo.builder()
                .asignacionCargoId(entity.getAsignacionCargoId())
                .legajoId(entity.getLegajoId())
                .categoriaId(entity.getCategoriaId())
                .dependenciaId(entity.getDependenciaId())
                .fechaAlta(entity.getFechaAlta())
                .fechaBaja(entity.getFechaBaja())
                .observaciones(entity.getObservaciones());
        if (entity.getCategoria() != null) {
            builder.categoriaNombre(entity.getCategoria().getNombre());
        }
        if (entity.getDependencia() != null) {
            builder.dependenciaNombre(entity.getDependencia().getNombre());
        }
        if (entity.getPersona() != null) {
            builder.personaApellido(entity.getPersona().getApellido());
            builder.personaNombre(entity.getPersona().getNombre());
        }
        return builder.build();
    }
}
