package um.haberes.core.hexagonal.liquidaciones.general.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.persistence.entity.LiquidacionGeneralDetalleEntity;

@Repository
public interface JpaLiquidacionGeneralDetalleRepository
        extends JpaRepository<LiquidacionGeneralDetalleEntity, Long> {

    List<LiquidacionGeneralDetalleEntity> findAllByProcesoIdOrderByLegajoId(String procesoId);

    List<LiquidacionGeneralDetalleEntity> findAllByProcesoIdAndResultadoOrderByLegajoId(String procesoId,
                                                                                        EstadoLegajoGeneral resultado);
}
