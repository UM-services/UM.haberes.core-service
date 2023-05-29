/**
 * 
 */
package um.haberes.rest.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.CargoLiquidacion;

/**
 * @author daniel
 *
 */
@Repository
public interface ICargoLiquidacionRepository extends JpaRepository<CargoLiquidacion, Long> {

	public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndSituacion(Long legajoId, Integer anho, Integer mes,
			String situacion);

	public List<CargoLiquidacion> findAllByAnhoAndMes(Integer anho, Integer mes, Sort sort);

	public List<CargoLiquidacion> findAllByAnhoAndMesAndSituacion(Integer anho, Integer mes, String situacion, Sort sort);

	public List<CargoLiquidacion> findAllByLegajoIdInAndCategoriaIdInAndAnhoAndMes(List<Long> legajos, List<Integer> categoriaIds,
			Integer anho, Integer mes);

	public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdIn(Long legajoId, Integer anho, Integer mes,
			List<Integer> categorias);

	public List<CargoLiquidacion> findAllByLegajoIdAndCategoriaIdInAndCategoriaBasicoGreaterThan(Long legajoId, List<Integer> categoriaIds,
			BigDecimal value);

	public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdNotIn(Long legajoId, Integer anho, Integer mes,
			List<Integer> categoriaIds);

	public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdBetween(Long legajoId, Integer anho,
			Integer mes, Integer categoriaIdDesde, Integer categoriaIdHasta);

	public Optional<CargoLiquidacion> findByLegajoIdAndAnhoAndMesAndCategoriaId(Long legajoId, Integer anho, Integer mes,
			Integer categoriaId);

	public Optional<CargoLiquidacion> findByCargoLiquidacionId(Long cargoLiquidacionId);

	@Modifying
	public void deleteAllByLegajoIdNotInAndAnhoAndMes(List<Long> legajos, Integer anho, Integer mes);

	@Modifying
	public void deleteAllByAnhoAndMes(Integer anho, Integer mes);

	@Modifying
	public void deleteAllByLegajoIdAndAnhoAndMesAndSituacionAndCategoriaIdIn(Long legajoId, Integer anho, Integer mes,
			String string, List<Integer> categoriaIds);

}
