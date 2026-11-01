package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.persistence.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.persistence.entity.AcreditacionPagoEntity;

@Repository
public interface JpaAcreditacionPagoRepository extends JpaRepository<AcreditacionPagoEntity, Long> {

    Optional<AcreditacionPagoEntity> findByAnhoAndMesAndFechaPago(Integer anho, Integer mes, OffsetDateTime fechaPago);

}
