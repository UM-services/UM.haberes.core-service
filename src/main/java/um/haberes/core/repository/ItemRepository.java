/**
 * 
 */
package um.haberes.core.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.Item;

/**
 * @author daniel
 *
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findAllByCodigoIdAndAnhoAndMes(Integer codigoId, Integer anho, Integer mes);

	List<Item> findAllByCodigoIdAndAnhoAndMesAndLegajoId(Integer codigoId, Integer anho, Integer mes,
			Long legajoId);

	List<Item> findAllByAnhoAndMes(Integer anho, Integer mes, Pageable pageable);

	List<Item> findAllByAnhoAndMesAndLegajoId(Integer anho, Integer mes, Long legajoId, Pageable pageable);

	List<Item> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	List<Item> findAllByLegajoIdAndAnhoAndMesAndCodigoIdIn(Long legajoId, Integer anho, Integer mes,
			List<Integer> codigoIds);

	List<Item> findAllByAnhoAndMesAndCodigoIdAndImporteGreaterThan(Integer anho, Integer mes, Integer codigoId,
			BigDecimal importe);

	Optional<Item> findByLegajoIdAndAnhoAndMesAndCodigoId(Long legajoId, Integer anho, Integer mes,
			Integer codigoId);

	@Modifying
    void deleteAllByAnhoAndMes(Integer anho, Integer mes);

	@Modifying
	void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	@Modifying
	void deleteAllByLegajoIdAndAnhoAndMesAndImporteAndCodigoIdLessThan(Long legajoId, Integer anho, Integer mes,
			BigDecimal importe, Integer codigoId);

    @Modifying
    void deleteAllByLegajoIdAndAnhoAndMesAndImporteAndCodigoIdGreaterThan(Long legajoId, Integer anho, Integer mes, BigDecimal importe, Integer codigoId);
}
