/**
 *
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.Liquidacion;

/**
 * @author daniel
 *
 */
@Repository
public interface LiquidacionRepository extends JpaRepository<Liquidacion, Long> {

    List<Liquidacion> findAllByAnhoAndMesOrderByLegajoId(Integer anho, Integer mes, Pageable pageable);

    List<Liquidacion> findAllByAnhoAndMesAndLegajoId(Integer anho, Integer mes, Long legajoId,
                                                     Pageable pageable);

    List<Liquidacion> findAllByAnhoAndMesAndLegajoIdIn(Integer anho, Integer mes, List<Long> legajoIds);

    List<Liquidacion> findAllByAnhoAndMesBetweenOrderByLegajoId(Integer anho, Integer mesDesde,
                                                                Integer mesHasta, Pageable pageable);

    List<Liquidacion> findAllByAnhoAndMesBetweenAndLegajoId(Integer anho, Integer mesDesde, Integer mesHasta,
                                                            Long legajoId, Pageable pageable);

    List<Liquidacion> findAllByAnhoAndMesBetween(Integer anho, Integer mesDesde, Integer mesHasta);

    List<Liquidacion> findAllByLegajoIdInAndAnhoAndMes(List<Long> legajoIds, Integer anho, Integer mes);

    List<Liquidacion> findAllByLegajoId(Long legajoId, Sort sort);

    List<Liquidacion> findAllByDependenciaIdAndAnhoAndMesAndSalida(Integer dependenciaId, Integer anho,
                                                                   Integer mes, String salida, Sort sort);

    List<Liquidacion> findAllByAnhoAndMesAndFechaAcreditacionNotNull(Integer anho, Integer mes, Sort sort);

    List<Liquidacion> findAllByAnhoAndMesAndFechaAcreditacionNotNullAndLegajoIdIn(Integer anho, Integer mes, List<Long> legajoIds, Sort and);

    Optional<Liquidacion> findByLiquidacionId(Long liquidacionId);

    Optional<Liquidacion> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    @Modifying
    void deleteAllByAnhoAndMes(Integer anho, Integer mes);

    @Modifying
    void deleteByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
