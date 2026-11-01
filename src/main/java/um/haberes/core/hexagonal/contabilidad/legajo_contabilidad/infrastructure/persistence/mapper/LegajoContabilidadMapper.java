package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.model.LegajoContabilidad;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.persistence.entity.LegajoContabilidadEntity;

@Component
public class LegajoContabilidadMapper {

    public LegajoContabilidadEntity toEntity(LegajoContabilidad domain) {
        if (domain == null) {
            return null;
        }
        LegajoContabilidadEntity.LegajoContabilidadEntityBuilder builder = LegajoContabilidadEntity
                .builder()
                .legajoContabilidadId(domain.getLegajoContabilidadId())
                .legajoId(domain.getLegajoId());
        if (domain.getAnho() != null) {
            builder.anho(domain.getAnho());
        }
        if (domain.getMes() != null) {
            builder.mes(domain.getMes());
        }
        if (domain.getDiferencia() != null) {
            builder.diferencia(domain.getDiferencia());
        }
        if (domain.getRemunerativo() != null) {
            builder.remunerativo(domain.getRemunerativo());
        }
        if (domain.getNoRemunerativo() != null) {
            builder.noRemunerativo(domain.getNoRemunerativo());
        }
        return builder.build();
    }

    public LegajoContabilidad toDomain(LegajoContabilidadEntity entity) {
        if (entity == null) {
            return null;
        }
        return LegajoContabilidad.builder()
                .legajoContabilidadId(entity.getLegajoContabilidadId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .diferencia(entity.getDiferencia())
                .remunerativo(entity.getRemunerativo())
                .noRemunerativo(entity.getNoRemunerativo())
                .build();
    }
}
