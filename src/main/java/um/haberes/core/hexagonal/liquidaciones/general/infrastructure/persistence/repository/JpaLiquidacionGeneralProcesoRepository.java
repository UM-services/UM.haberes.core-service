package um.haberes.core.hexagonal.liquidaciones.general.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.TipoProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.persistence.entity.LiquidacionGeneralProcesoEntity;

@Repository
public interface JpaLiquidacionGeneralProcesoRepository
        extends JpaRepository<LiquidacionGeneralProcesoEntity, String> {

    List<LiquidacionGeneralProcesoEntity> findAllByAnhoAndMesOrderByIniciadoEnDesc(Integer anho, Integer mes);

    List<LiquidacionGeneralProcesoEntity> findAllByAnhoAndMesAndEstado(Integer anho, Integer mes,
                                                                       EstadoProcesoGeneral estado);

    boolean existsByAnhoAndMesAndEstado(Integer anho, Integer mes, EstadoProcesoGeneral estado);

    boolean existsByAnhoAndMesAndTipoAndEstado(Integer anho, Integer mes, TipoProcesoGeneral tipo,
                                               EstadoProcesoGeneral estado);
}
