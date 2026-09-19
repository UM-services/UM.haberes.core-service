package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.adapter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.entity.CargoLiquidacionEntity;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.mapper.CargoLiquidacionMapper;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.repository.JpaCargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class JpaCargoLiquidacionRepositoryAdapter implements CargoLiquidacionRepository {

    private static final Sort ACTIVOS_ORDER = Sort.by("legajoId").ascending()
            .and(Sort.by("dependenciaId").ascending().and(Sort.by("categoriaId").ascending()));

    private final JpaCargoLiquidacionRepository jpaCargoLiquidacionRepository;
    private final CargoLiquidacionMapper cargoLiquidacionMapper;

    @Override
    public CargoLiquidacion save(CargoLiquidacion cargoLiquidacion) {
        return cargoLiquidacionMapper.toDomain(
                jpaCargoLiquidacionRepository.save(cargoLiquidacionMapper.toEntity(cargoLiquidacion)));
    }

    @Override
    public List<CargoLiquidacion> saveAll(List<CargoLiquidacion> cargos) {
        List<CargoLiquidacionEntity> entities = cargos.stream()
                .map(cargoLiquidacionMapper::toEntity)
                .collect(Collectors.toList());
        return toDomainList(jpaCargoLiquidacionRepository.saveAll(entities));
    }

    @Override
    public Optional<CargoLiquidacion> findByCargoLiquidacionId(Long cargoLiquidacionId) {
        return jpaCargoLiquidacionRepository.findByCargoLiquidacionId(cargoLiquidacionId).map(cargoLiquidacionMapper::toDomain);
    }

    @Override
    public Optional<CargoLiquidacion> findByLegajoIdAndAnhoAndMesAndCategoriaId(Long legajoId, Integer anho,
            Integer mes, Integer categoriaId) {
        return jpaCargoLiquidacionRepository.findByLegajoIdAndAnhoAndMesAndCategoriaId(legajoId, anho, mes, categoriaId)
                .map(cargoLiquidacionMapper::toDomain);
    }

    @Override
    public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        return toDomainList(jpaCargoLiquidacionRepository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes));
    }

    @Override
    public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaDocente(Long legajoId, Integer anho,
            Integer mes, Byte docente) {
        return toDomainList(
                jpaCargoLiquidacionRepository.findAllByLegajoIdAndAnhoAndMesAndCategoriaDocente(legajoId, anho, mes,
                        docente));
    }

    @Override
    public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaNoDocente(Long legajoId, Integer anho,
            Integer mes, Byte noDocente) {
        return toDomainList(
                jpaCargoLiquidacionRepository.findAllByLegajoIdAndAnhoAndMesAndCategoriaNoDocente(legajoId, anho, mes,
                        noDocente));
    }

    @Override
    public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndDependenciaFacultadIdAndCategoriaDocente(
            Long legajoId, Integer anho, Integer mes, Integer facultadId, Byte docente) {
        return toDomainList(jpaCargoLiquidacionRepository
                .findAllByLegajoIdAndAnhoAndMesAndDependenciaFacultadIdAndCategoriaDocente(legajoId, anho, mes,
                        facultadId, docente));
    }

    @Override
    public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndDependenciaFacultadIdAndCategoriaNoDocente(
            Long legajoId, Integer anho, Integer mes, Integer facultadId, Byte noDocente) {
        return toDomainList(jpaCargoLiquidacionRepository
                .findAllByLegajoIdAndAnhoAndMesAndDependenciaFacultadIdAndCategoriaNoDocente(legajoId, anho, mes,
                        facultadId, noDocente));
    }

    @Override
    public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdBetween(Long legajoId, Integer anho,
            Integer mes, Integer categoriaIdDesde, Integer categoriaIdHasta) {
        return toDomainList(jpaCargoLiquidacionRepository.findAllByLegajoIdAndAnhoAndMesAndCategoriaIdBetween(legajoId,
                anho, mes, categoriaIdDesde, categoriaIdHasta));
    }

    @Override
    public List<CargoLiquidacion> findAllByLegajoIdAndCategoriaIdInAndCategoriaBasicoGreaterThan(Long legajoId,
            List<Integer> categoriaIds, BigDecimal basico) {
        return toDomainList(jpaCargoLiquidacionRepository
                .findAllByLegajoIdAndCategoriaIdInAndCategoriaBasicoGreaterThan(legajoId, categoriaIds, basico));
    }

    @Override
    public List<CargoLiquidacion> findAllByAnhoAndMesAndSituacion(Integer anho, Integer mes, String situacion) {
        return toDomainList(jpaCargoLiquidacionRepository.findAllByAnhoAndMesAndSituacion(anho, mes, situacion,
                ACTIVOS_ORDER));
    }

    @Override
    public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndSituacion(Long legajoId, Integer anho, Integer mes,
            String situacion) {
        return toDomainList(
                jpaCargoLiquidacionRepository.findAllByLegajoIdAndAnhoAndMesAndSituacion(legajoId, anho, mes,
                        situacion));
    }

    @Override
    public List<CargoLiquidacion> findAllByLegajoIdInAndCategoriaIdInAndAnhoAndMes(List<Long> legajoIds,
            List<Integer> categoriaIds, Integer anho, Integer mes) {
        return toDomainList(jpaCargoLiquidacionRepository.findAllByLegajoIdInAndCategoriaIdInAndAnhoAndMes(legajoIds,
                categoriaIds, anho, mes));
    }

    @Override
    public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdIn(Long legajoId, Integer anho,
            Integer mes, List<Integer> categoriaIds) {
        return toDomainList(
                jpaCargoLiquidacionRepository.findAllByLegajoIdAndAnhoAndMesAndCategoriaIdIn(legajoId, anho, mes,
                        categoriaIds));
    }

    @Override
    public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdNotIn(Long legajoId, Integer anho,
            Integer mes, List<Integer> categoriaIds) {
        return toDomainList(
                jpaCargoLiquidacionRepository.findAllByLegajoIdAndAnhoAndMesAndCategoriaIdNotIn(legajoId, anho, mes,
                        categoriaIds));
    }

    @Override
    public void deleteAllByLegajoIdNotInAndAnhoAndMes(List<Long> legajoIds, Integer anho, Integer mes) {
        jpaCargoLiquidacionRepository.deleteAllByLegajoIdNotInAndAnhoAndMes(legajoIds, anho, mes);
    }

    @Override
    public void deleteAllByAnhoAndMes(Integer anho, Integer mes) {
        jpaCargoLiquidacionRepository.deleteAllByAnhoAndMes(anho, mes);
    }

    @Override
    public void deleteAllByLegajoIdAndAnhoAndMesAndSituacionAndCategoriaIdIn(Long legajoId, Integer anho, Integer mes,
            String situacion, List<Integer> categoriaIds) {
        jpaCargoLiquidacionRepository.deleteAllByLegajoIdAndAnhoAndMesAndSituacionAndCategoriaIdIn(legajoId, anho, mes,
                situacion, categoriaIds);
    }

    private List<CargoLiquidacion> toDomainList(List<CargoLiquidacionEntity> entities) {
        return entities.stream()
                .map(cargoLiquidacionMapper::toDomain)
                .collect(Collectors.toList());
    }
}
