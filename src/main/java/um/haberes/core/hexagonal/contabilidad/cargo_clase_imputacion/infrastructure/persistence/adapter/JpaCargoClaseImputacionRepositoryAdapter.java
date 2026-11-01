package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model.CargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.ports.out.CargoClaseImputacionRepository;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.persistence.entity.CargoClaseImputacionEntity;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.persistence.mapper.CargoClaseImputacionMapper;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.persistence.repository.JpaCargoClaseImputacionRepository;

@Component
@RequiredArgsConstructor
public class JpaCargoClaseImputacionRepositoryAdapter implements CargoClaseImputacionRepository {

    private final JpaCargoClaseImputacionRepository jpaCargoClaseImputacionRepository;
    private final CargoClaseImputacionMapper cargoClaseImputacionMapper;

    @Override
    public CargoClaseImputacion create(CargoClaseImputacion cargoClaseImputacion) {
        CargoClaseImputacionEntity entity = cargoClaseImputacionMapper.toEntity(cargoClaseImputacion);
        return cargoClaseImputacionMapper.toDomain(jpaCargoClaseImputacionRepository.save(entity));
    }

    @Override
    public Optional<CargoClaseImputacion> findById(Long cargoClaseImputacionId) {
        return jpaCargoClaseImputacionRepository.findByCargoClaseImputacionId(cargoClaseImputacionId)
                .map(cargoClaseImputacionMapper::toDomain);
    }

    @Override
    public Optional<CargoClaseImputacion> findByUnique(Integer dependenciaId, Integer facultadId, Integer geograficaId,
            Long cargoClaseId) {
        return jpaCargoClaseImputacionRepository
                .findByDependenciaIdAndFacultadIdAndGeograficaIdAndCargoClaseId(dependenciaId, facultadId,
                        geograficaId, cargoClaseId)
                .map(cargoClaseImputacionMapper::toDomain);
    }

    @Override
    public List<CargoClaseImputacion> findAll() {
        return jpaCargoClaseImputacionRepository.findAll().stream()
                .map(cargoClaseImputacionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CargoClaseImputacion> update(Long cargoClaseImputacionId, CargoClaseImputacion cargoClaseImputacion) {
        if (jpaCargoClaseImputacionRepository.existsById(cargoClaseImputacionId)) {
            CargoClaseImputacionEntity entity = cargoClaseImputacionMapper.toEntity(cargoClaseImputacion);
            entity.setCargoClaseImputacionId(cargoClaseImputacionId);
            CargoClaseImputacionEntity updatedEntity = jpaCargoClaseImputacionRepository.save(entity);
            return Optional.of(cargoClaseImputacionMapper.toDomain(updatedEntity));
        }
        return Optional.empty();
    }
}
