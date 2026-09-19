/**
 * 
 */
package um.haberes.core.hexagonal.liquidaciones.item.infrastructure.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.liquidaciones.item.infrastructure.persistence.entity.ItemEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaItemRepository extends JpaRepository<ItemEntity, Long> {

	List<ItemEntity> findAllByCodigoIdAndAnhoAndMes(Integer codigoId, Integer anho, Integer mes);

	List<ItemEntity> findAllByCodigoIdAndAnhoAndMesAndLegajoId(Integer codigoId, Integer anho, Integer mes,
			Long legajoId);

	List<ItemEntity> findAllByAnhoAndMes(Integer anho, Integer mes, Pageable pageable);

	List<ItemEntity> findAllByAnhoAndMesAndLegajoId(Integer anho, Integer mes, Long legajoId, Pageable pageable);

	List<ItemEntity> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	List<ItemEntity> findAllByLegajoIdAndAnhoAndMesAndCodigoIdIn(Long legajoId, Integer anho, Integer mes,
			List<Integer> codigoIds);

	List<ItemEntity> findAllByAnhoAndMesAndCodigoIdAndImporteGreaterThan(Integer anho, Integer mes, Integer codigoId,
			BigDecimal importe);

	Optional<ItemEntity> findByLegajoIdAndAnhoAndMesAndCodigoId(Long legajoId, Integer anho, Integer mes,
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
