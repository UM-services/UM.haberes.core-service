package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.infrastructure.persistence.entity.LegajoCodigoImputacionEntity;

@Repository
public interface JpaLegajoCodigoImputacionRepository
        extends JpaRepository<LegajoCodigoImputacionEntity, Long> {

    List<LegajoCodigoImputacionEntity> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    List<LegajoCodigoImputacionEntity> findAllByLegajoIdAndAnhoAndMesAndCodigoIdIn(Long legajoId, Integer anho,
            Integer mes, List<Integer> codigoIds);

    void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    void deleteAllByAnhoAndMes(Integer anho, Integer mes);
}
