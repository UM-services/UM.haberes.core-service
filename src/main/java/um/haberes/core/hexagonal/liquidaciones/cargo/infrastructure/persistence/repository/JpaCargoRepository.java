package um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.persistence.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.persistence.entity.CargoEntity;

@Repository
public interface JpaCargoRepository extends JpaRepository<CargoEntity, Long> {

    List<CargoEntity> findAllByLegajoIdOrderByCargoIdDesc(Long legajoId);

    List<CargoEntity> findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaIsNullAndCategoriaDocente(Long legajoId,
            OffsetDateTime firstDate, Byte docente);

    List<CargoEntity> findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaGreaterThanEqualAndCategoriaDocente(
            Long legajoId, OffsetDateTime firstDate, OffsetDateTime lastDate, Byte docente);

    List<CargoEntity> findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaIsNullAndCategoriaNoDocente(Long legajoId,
            OffsetDateTime firstDate, Byte noDocente);

    List<CargoEntity> findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaGreaterThanEqualAndCategoriaNoDocente(
            Long legajoId, OffsetDateTime firstDate, OffsetDateTime lastDate, Byte noDocente);

    Optional<CargoEntity> findByCargoId(Long cargoId);

    @Modifying
    void deleteByCargoId(Long cargoId);

}
