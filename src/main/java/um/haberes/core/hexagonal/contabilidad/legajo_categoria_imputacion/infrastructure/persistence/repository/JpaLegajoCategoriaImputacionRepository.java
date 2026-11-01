package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.infrastructure.persistence.entity.LegajoCategoriaImputacionEntity;

@Repository
public interface JpaLegajoCategoriaImputacionRepository
        extends JpaRepository<LegajoCategoriaImputacionEntity, Long> {

    List<LegajoCategoriaImputacionEntity> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    void deleteAllByAnhoAndMes(Integer anho, Integer mes);
}
