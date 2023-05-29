/**
 * 
 */
package um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.Liquidacion;

/**
 * @author daniel
 *
 */
@Repository
public interface ILiquidacionRepository extends JpaRepository<Liquidacion, Long> {

	public List<Liquidacion> findAllByAnhoAndMesOrderByLegajoId(Integer anho, Integer mes, Pageable pageable);

	public List<Liquidacion> findAllByAnhoAndMesAndLegajoId(Integer anho, Integer mes, Long legajoId,
			Pageable pageable);

	public List<Liquidacion> findAllByAnhoAndMesBetweenOrderByLegajoId(Integer anho, Integer mesDesde,
			Integer mesHasta, Pageable pageable);

	public List<Liquidacion> findAllByAnhoAndMesBetweenAndLegajoId(Integer anho, Integer mesDesde, Integer mesHasta,
			Long legajoId, Pageable pageable);

	public List<Liquidacion> findAllByAnhoAndMesBetween(Integer anho, Integer mesDesde, Integer mesHasta);

	public List<Liquidacion> findAllByLegajoIdInAndAnhoAndMes(List<Long> legajoIds, Integer anho, Integer mes);

	public List<Liquidacion> findAllByLegajoId(Long legajoId, Sort sort);

	public List<Liquidacion> findAllByDependenciaIdAndAnhoAndMesAndSalida(Integer dependenciaId, Integer anho,
			Integer mes, String salida, Sort sort);

	public List<Liquidacion> findAllByAnhoAndMesAndFechaAcreditacionNotNull(Integer anho, Integer mes, Sort sort);

	public Optional<Liquidacion> findByLiquidacionId(Long liquidacionId);

	public Optional<Liquidacion> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	@Modifying
	public void deleteAllByAnhoAndMes(Integer anho, Integer mes);

	@Modifying
	public void deleteByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
