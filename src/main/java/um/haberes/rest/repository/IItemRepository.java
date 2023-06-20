/**
 * 
 */
package um.haberes.rest.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.Item;

/**
 * @author daniel
 *
 */
@Repository
public interface IItemRepository extends JpaRepository<Item, Long> {

	public List<Item> findAllByCodigoIdAndAnhoAndMes(Integer codigoId, Integer anho, Integer mes);

	public List<Item> findAllByCodigoIdAndAnhoAndMesAndLegajoId(Integer codigoId, Integer anho, Integer mes,
			Long legajoId);

	public List<Item> findAllByAnhoAndMes(Integer anho, Integer mes, Pageable pageable);

	public List<Item> findAllByAnhoAndMesAndLegajoId(Integer anho, Integer mes, Long legajoId, Pageable pageable);

	public List<Item> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	public List<Item> findAllByLegajoIdAndAnhoAndMesAndCodigoIdIn(Long legajoId, Integer anho, Integer mes,
			List<Integer> codigoIds);

	public List<Item> findAllByAnhoAndMesAndCodigoIdAndImporteGreaterThan(Integer anho, Integer mes, Integer codigoId,
			BigDecimal importe);

	public Optional<Item> findByLegajoIdAndAnhoAndMesAndCodigoId(Long legajoId, Integer anho, Integer mes,
			Integer codigoId);

	@Modifying
	public void deleteAllByAnhoAndMes(Integer anho, Integer mes);

	@Modifying
	public void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	@Modifying
	public void deleteAllByLegajoIdAndAnhoAndMesAndImporteAndCodigoIdLessThan(Long legajoId, Integer anho, Integer mes,
			BigDecimal importe, Integer codigoId);

}
