package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.persistence.entity.CargoClaseImputacionEntity;

@Repository
public interface JpaCargoClaseImputacionRepository
        extends JpaRepository<CargoClaseImputacionEntity, Long> {

    Optional<CargoClaseImputacionEntity> findByCargoClaseImputacionId(Long cargoClaseImputacionId);

    Optional<CargoClaseImputacionEntity> findByDependenciaIdAndFacultadIdAndGeograficaIdAndCargoClaseId(
            Integer dependenciaId, Integer facultadId, Integer geograficaId, Long cargoClaseId);
}
