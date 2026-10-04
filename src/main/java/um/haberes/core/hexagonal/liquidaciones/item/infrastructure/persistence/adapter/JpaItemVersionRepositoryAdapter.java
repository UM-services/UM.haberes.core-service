package um.haberes.core.hexagonal.liquidaciones.item.infrastructure.persistence.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.ItemVersion;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemVersionRepository;
import um.haberes.core.model.ItemVersionEntity;
import um.haberes.core.repository.JpaItemVersionRepository;

@Component
@RequiredArgsConstructor
public class JpaItemVersionRepositoryAdapter implements ItemVersionRepository {

    private final JpaItemVersionRepository jpaItemVersionRepository;

    @Override
    public ItemVersion save(ItemVersion itemVersion) {
        return toDomain(jpaItemVersionRepository.save(toEntity(itemVersion)));
    }

    @Override
    public List<ItemVersion> saveAll(List<ItemVersion> itemVersions) {
        List<ItemVersionEntity> entities = itemVersions.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        return jpaItemVersionRepository.saveAll(entities).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private ItemVersionEntity toEntity(ItemVersion domain) {
        if (domain == null) {
            return null;
        }
        return new ItemVersionEntity(null, domain.getLegajoId(), domain.getAnho(), domain.getMes(),
                domain.getCodigoId(), domain.getCodigoNombre(), domain.getImporte());
    }

    private ItemVersion toDomain(ItemVersionEntity entity) {
        if (entity == null) {
            return null;
        }
        return ItemVersion.builder()
                .itemVersionId(entity.getItemVersionId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .codigoId(entity.getCodigoId())
                .codigoNombre(entity.getCodigoNombre())
                .importe(entity.getImporte())
                .build();
    }
}
