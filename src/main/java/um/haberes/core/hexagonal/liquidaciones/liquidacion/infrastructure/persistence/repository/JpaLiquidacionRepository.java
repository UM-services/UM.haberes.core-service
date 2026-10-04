/**
 *
 */
package um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.entity.LiquidacionEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaLiquidacionRepository extends JpaRepository<LiquidacionEntity, Long> {

    List<LiquidacionEntity> findAllByAnhoAndMesOrderByLegajoId(Integer anho, Integer mes, Pageable pageable);

    List<LiquidacionEntity> findAllByAnhoAndMesAndLegajoId(Integer anho, Integer mes, Long legajoId,
                                                     Pageable pageable);

    List<LiquidacionEntity> findAllByAnhoAndMesAndLegajoIdIn(Integer anho, Integer mes, List<Long> legajoIds);

    List<LiquidacionEntity> findAllByAnhoAndMesBetweenOrderByLegajoId(Integer anho, Integer mesDesde,
                                                                Integer mesHasta, Pageable pageable);

    List<LiquidacionEntity> findAllByAnhoAndMesBetweenAndLegajoId(Integer anho, Integer mesDesde, Integer mesHasta,
                                                            Long legajoId, Pageable pageable);

    List<LiquidacionEntity> findAllByAnhoAndMesBetween(Integer anho, Integer mesDesde, Integer mesHasta);

    List<LiquidacionEntity> findAllByLegajoIdInAndAnhoAndMes(List<Long> legajoIds, Integer anho, Integer mes);

    List<LiquidacionEntity> findAllByLegajoId(Long legajoId, Sort sort);

    List<LiquidacionEntity> findAllByDependenciaIdAndAnhoAndMesAndSalida(Integer dependenciaId, Integer anho,
                                                                   Integer mes, String salida, Sort sort);

    List<LiquidacionEntity> findAllByAnhoAndMesAndFechaAcreditacionNotNull(Integer anho, Integer mes, Sort sort);

    List<LiquidacionEntity> findAllByAnhoAndMesAndFechaAcreditacionNotNullAndLegajoIdIn(Integer anho, Integer mes, List<Long> legajoIds, Sort and);

    Optional<LiquidacionEntity> findByLiquidacionId(Long liquidacionId);

    Optional<LiquidacionEntity> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    @Modifying
    void deleteAllByAnhoAndMes(Integer anho, Integer mes);

    @Modifying
    void deleteByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
