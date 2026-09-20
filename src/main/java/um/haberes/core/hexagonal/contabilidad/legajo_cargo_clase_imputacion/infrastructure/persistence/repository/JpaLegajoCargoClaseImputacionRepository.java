package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.persistence.entity.LegajoCargoClaseImputacionEntity;

@Repository
public interface JpaLegajoCargoClaseImputacionRepository
        extends JpaRepository<LegajoCargoClaseImputacionEntity, Long> {

    List<LegajoCargoClaseImputacionEntity> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    void deleteAllByAnhoAndMes(Integer anho, Integer mes);
}
