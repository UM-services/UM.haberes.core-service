package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.model.LegajoContabilidad;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.ports.out.LegajoContabilidadRepository;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.persistence.entity.LegajoContabilidadEntity;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.persistence.mapper.LegajoContabilidadMapper;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.persistence.repository.JpaLegajoContabilidadRepository;

@Component
@RequiredArgsConstructor
public class JpaLegajoContabilidadRepositoryAdapter implements LegajoContabilidadRepository {

    private static final byte DIFERENCIA_FLAG = 1;

    private final JpaLegajoContabilidadRepository jpaLegajoContabilidadRepository;
    private final LegajoContabilidadMapper legajoContabilidadMapper;

    @Override
    public List<LegajoContabilidad> findAllDiferenciaByPeriodo(Integer anho, Integer mes) {
        return jpaLegajoContabilidadRepository.findAllByAnhoAndMesAndDiferencia(anho, mes, DIFERENCIA_FLAG).stream()
                .map(legajoContabilidadMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LegajoContabilidad> findByUnique(Long legajoId, Integer anho, Integer mes) {
        return jpaLegajoContabilidadRepository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes)
                .map(legajoContabilidadMapper::toDomain);
    }

    @Override
    public LegajoContabilidad create(LegajoContabilidad legajoContabilidad) {
        LegajoContabilidadEntity entity = legajoContabilidadMapper.toEntity(legajoContabilidad);
        return legajoContabilidadMapper.toDomain(jpaLegajoContabilidadRepository.save(entity));
    }

    @Override
    public Optional<LegajoContabilidad> update(Long legajoContabilidadId, LegajoContabilidad legajoContabilidad) {
        if (jpaLegajoContabilidadRepository.existsById(legajoContabilidadId)) {
            LegajoContabilidadEntity entity = legajoContabilidadMapper.toEntity(legajoContabilidad);
            entity.setLegajoContabilidadId(legajoContabilidadId);
            LegajoContabilidadEntity updatedEntity = jpaLegajoContabilidadRepository.save(entity);
            return Optional.of(legajoContabilidadMapper.toDomain(updatedEntity));
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long legajoContabilidadId) {
        jpaLegajoContabilidadRepository.deleteById(legajoContabilidadId);
    }
}
