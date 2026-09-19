/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.LegajoControlEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaLegajoControlRepository extends JpaRepository<LegajoControlEntity, Long> {

	public List<LegajoControlEntity> findAllByAnhoAndMes(Integer anho, Integer mes);

	public List<LegajoControlEntity> findAllByAnhoAndMes(Integer anho, Integer mes, Sort sort);

	public List<LegajoControlEntity> findAllByAnhoAndMesAndLiquidadoOrderByLegajoId(Integer anho, Integer mes,
			Byte liquidado);

	public Optional<LegajoControlEntity> findByLegajoControlId(Long legajoControlId);

	public Optional<LegajoControlEntity> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
