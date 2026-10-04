package um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.LiquidacionVersion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionVersionRepository;
import um.haberes.core.model.LiquidacionVersionEntity;
import um.haberes.core.repository.JpaLiquidacionVersionRepository;

@Component
@RequiredArgsConstructor
public class JpaLiquidacionVersionRepositoryAdapter implements LiquidacionVersionRepository {

    private final JpaLiquidacionVersionRepository jpaLiquidacionVersionRepository;

    @Override
    public LiquidacionVersion save(LiquidacionVersion liquidacionVersion) {
        return toDomain(jpaLiquidacionVersionRepository.save(toEntity(liquidacionVersion)));
    }

    @Override
    public List<LiquidacionVersion> saveAll(List<LiquidacionVersion> liquidacionVersions) {
        List<LiquidacionVersionEntity> entities = liquidacionVersions.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        return jpaLiquidacionVersionRepository.saveAll(entities).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private LiquidacionVersionEntity toEntity(LiquidacionVersion domain) {
        if (domain == null) {
            return null;
        }
        return new LiquidacionVersionEntity(null, domain.getLegajoId(), domain.getAnho(), domain.getMes(),
                domain.getVersion(), domain.getFechaLiquidacion(), domain.getFechaAcreditacion(),
                domain.getDependenciaId(), domain.getSalida(), domain.getTotalRemunerativo(),
                domain.getTotalNoRemunerativo(), domain.getTotalDeduccion(), domain.getTotalNeto(),
                domain.getBloqueado(), domain.getEstado(), domain.getLiquida());
    }

    private LiquidacionVersion toDomain(LiquidacionVersionEntity entity) {
        if (entity == null) {
            return null;
        }
        LiquidacionVersion.LiquidacionVersionBuilder builder = LiquidacionVersion.builder()
                .liquidacionVersionId(entity.getLiquidacionVersionId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .version(entity.getVersion())
                .fechaLiquidacion(entity.getFechaLiquidacion())
                .fechaAcreditacion(entity.getFechaAcreditacion())
                .dependenciaId(entity.getDependenciaId())
                .salida(entity.getSalida())
                .estado(entity.getEstado());
        if (entity.getTotalRemunerativo() != null) {
            builder.totalRemunerativo(entity.getTotalRemunerativo());
        }
        if (entity.getTotalNoRemunerativo() != null) {
            builder.totalNoRemunerativo(entity.getTotalNoRemunerativo());
        }
        if (entity.getTotalDeduccion() != null) {
            builder.totalDeduccion(entity.getTotalDeduccion());
        }
        if (entity.getTotalNeto() != null) {
            builder.totalNeto(entity.getTotalNeto());
        }
        if (entity.getBloqueado() != null) {
            builder.bloqueado(entity.getBloqueado());
        }
        if (entity.getLiquida() != null) {
            builder.liquida(entity.getLiquida());
        }
        return builder.build();
    }
}
