package um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.entity.LiquidacionEntity;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.mapper.LiquidacionMapper;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.repository.JpaLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class JpaLiquidacionRepositoryAdapter implements LiquidacionRepository {

    private static final int FULL_LIMIT = 999999;

    private static final Sort PERSONA_ORDEN = Sort.by("persona.apellido").ascending()
            .and(Sort.by("persona.nombre").ascending());

    private final JpaLiquidacionRepository jpaLiquidacionRepository;
    private final LiquidacionMapper liquidacionMapper;

    @Override
    public Liquidacion save(Liquidacion liquidacion) {
        return liquidacionMapper.toDomain(jpaLiquidacionRepository.save(liquidacionMapper.toEntity(liquidacion)));
    }

    @Override
    public List<Liquidacion> saveAll(List<Liquidacion> liquidaciones) {
        List<LiquidacionEntity> entities = liquidaciones.stream()
                .map(liquidacionMapper::toEntity)
                .collect(Collectors.toList());
        return toDomainList(jpaLiquidacionRepository.saveAll(entities));
    }

    @Override
    public Optional<Liquidacion> findByLiquidacionId(Long liquidacionId) {
        return jpaLiquidacionRepository.findByLiquidacionId(liquidacionId).map(liquidacionMapper::toDomain);
    }

    @Override
    public Optional<Liquidacion> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        return jpaLiquidacionRepository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes).map(liquidacionMapper::toDomain);
    }

    @Override
    public List<Liquidacion> findAllByAnhoAndMes(Integer anho, Integer mes, Integer limit) {
        return toDomainList(jpaLiquidacionRepository.findAllByAnhoAndMesOrderByLegajoId(anho, mes,
                PageRequest.of(0, effectiveLimit(limit))));
    }

    @Override
    public List<Liquidacion> findAllByAnhoAndMesAndLegajoId(Integer anho, Integer mes, Long legajoId, Integer limit) {
        return toDomainList(jpaLiquidacionRepository.findAllByAnhoAndMesAndLegajoId(anho, mes, legajoId,
                PageRequest.of(0, effectiveLimit(limit))));
    }

    @Override
    public List<Liquidacion> findAllByAnhoAndMesAndLegajoIdIn(Integer anho, Integer mes, List<Long> legajoIds) {
        return toDomainList(jpaLiquidacionRepository.findAllByAnhoAndMesAndLegajoIdIn(anho, mes, legajoIds));
    }

    @Override
    public List<Liquidacion> findAllByAnhoAndMesBetween(Integer anho, Integer mesDesde, Integer mesHasta) {
        return toDomainList(jpaLiquidacionRepository.findAllByAnhoAndMesBetween(anho, mesDesde, mesHasta));
    }

    @Override
    public List<Liquidacion> findAllByAnhoAndMesBetweenOrderByLegajoId(Integer anho, Integer mesDesde, Integer mesHasta, Integer limit) {
        return toDomainList(jpaLiquidacionRepository.findAllByAnhoAndMesBetweenOrderByLegajoId(anho, mesDesde,
                mesHasta, PageRequest.of(0, effectiveLimit(limit))));
    }

    @Override
    public List<Liquidacion> findAllByAnhoAndMesBetweenAndLegajoId(Integer anho, Integer mesDesde, Integer mesHasta, Long legajoId, Integer limit) {
        return toDomainList(jpaLiquidacionRepository.findAllByAnhoAndMesBetweenAndLegajoId(anho, mesDesde,
                mesHasta, legajoId, PageRequest.of(0, effectiveLimit(limit))));
    }

    @Override
    public List<Liquidacion> findAllByLegajoIdInAndAnhoAndMes(List<Long> legajoIds, Integer anho, Integer mes) {
        return toDomainList(jpaLiquidacionRepository.findAllByLegajoIdInAndAnhoAndMes(legajoIds, anho, mes));
    }

    @Override
    public List<Liquidacion> findAllByLegajoId(Long legajoId) {
        return toDomainList(jpaLiquidacionRepository.findAllByLegajoId(legajoId,
                Sort.by("anho").ascending().and(Sort.by("mes").ascending())));
    }

    @Override
    public List<Liquidacion> findAllByDependenciaIdAndAnhoAndMesAndSalida(Integer dependenciaId, Integer anho, Integer mes, String salida) {
        return toDomainList(jpaLiquidacionRepository.findAllByDependenciaIdAndAnhoAndMesAndSalida(dependenciaId,
                anho, mes, salida, PERSONA_ORDEN));
    }

    @Override
    public List<Liquidacion> findAllByAnhoAndMesAndFechaAcreditacionNotNull(Integer anho, Integer mes) {
        return toDomainList(jpaLiquidacionRepository.findAllByAnhoAndMesAndFechaAcreditacionNotNull(anho, mes,
                PERSONA_ORDEN));
    }

    @Override
    public List<Liquidacion> findAllByAnhoAndMesAndFechaAcreditacionNotNullAndLegajoIdIn(Integer anho, Integer mes, List<Long> legajoIds) {
        return toDomainList(jpaLiquidacionRepository.findAllByAnhoAndMesAndFechaAcreditacionNotNullAndLegajoIdIn(
                anho, mes, legajoIds, PERSONA_ORDEN));
    }

    @Override
    public void deleteAllByAnhoAndMes(Integer anho, Integer mes) {
        jpaLiquidacionRepository.deleteAllByAnhoAndMes(anho, mes);
    }

    @Override
    public void deleteByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        jpaLiquidacionRepository.deleteByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }

    private int effectiveLimit(Integer limit) {
        return limit == null || limit == 0 ? FULL_LIMIT : limit;
    }

    private List<Liquidacion> toDomainList(List<LiquidacionEntity> entities) {
        return entities.stream()
                .map(liquidacionMapper::toDomain)
                .collect(Collectors.toList());
    }
}
