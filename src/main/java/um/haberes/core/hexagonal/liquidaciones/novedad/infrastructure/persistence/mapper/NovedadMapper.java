package um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.persistence.entity.NovedadEntity;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.mapper.DependenciaMapper;

@Component
@RequiredArgsConstructor
public class NovedadMapper {

    private final DependenciaMapper dependenciaMapper;

    public NovedadEntity toEntity(Novedad domain) {
        if (domain == null) {
            return null;
        }
        NovedadEntity entity = new NovedadEntity();
        entity.setNovedadId(domain.getNovedadId());
        entity.setLegajoId(domain.getLegajoId());
        if (domain.getAnho() != null) {
            entity.setAnho(domain.getAnho());
        }
        if (domain.getMes() != null) {
            entity.setMes(domain.getMes());
        }
        entity.setCodigoId(domain.getCodigoId());
        entity.setDependenciaId(domain.getDependenciaId());
        if (domain.getImporte() != null) {
            entity.setImporte(domain.getImporte());
        }
        if (domain.getValue() != null) {
            entity.setValue(domain.getValue());
        }
        entity.setObservaciones(domain.getObservaciones());
        entity.setImportado(domain.getImportado());
        entity.setNovedadUploadId(domain.getNovedadUploadId());
        return entity;
    }

    public Novedad toDomain(NovedadEntity entity) {
        if (entity == null) {
            return null;
        }
        Novedad.NovedadBuilder builder = Novedad.builder()
                .novedadId(entity.getNovedadId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .codigoId(entity.getCodigoId())
                .dependenciaId(entity.getDependenciaId())
                .dependencia(dependenciaMapper.toDomain(entity.getDependencia()))
                .observaciones(entity.getObservaciones())
                .importado(entity.getImportado())
                .novedadUploadId(entity.getNovedadUploadId());
        if (entity.getImporte() != null) {
            builder.importe(entity.getImporte());
        }
        if (entity.getValue() != null) {
            builder.value(entity.getValue());
        }
        return builder.build();
    }
}
