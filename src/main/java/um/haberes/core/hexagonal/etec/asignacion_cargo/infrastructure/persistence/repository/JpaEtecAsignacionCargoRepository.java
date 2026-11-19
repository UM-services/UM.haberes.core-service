package um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.persistence.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.persistence.entity.EtecAsignacionCargoEntity;

@Repository
public interface JpaEtecAsignacionCargoRepository extends JpaRepository<EtecAsignacionCargoEntity, Long> {

    List<EtecAsignacionCargoEntity> findAllByLegajoIdOrderByFechaAltaDesc(Long legajoId);

    List<EtecAsignacionCargoEntity> findAllByFechaAltaLessThanEqualAndFechaBajaIsNull(OffsetDateTime firstDay);

    List<EtecAsignacionCargoEntity> findAllByFechaAltaLessThanEqualAndFechaBajaGreaterThanEqual(
            OffsetDateTime firstDay, OffsetDateTime lastDay);

    List<EtecAsignacionCargoEntity> findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaIsNull(
            Long legajoId, OffsetDateTime firstDay);

    List<EtecAsignacionCargoEntity> findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaGreaterThanEqual(
            Long legajoId, OffsetDateTime firstDay, OffsetDateTime lastDay);

    boolean existsByCategoriaId(Integer categoriaId);
}
