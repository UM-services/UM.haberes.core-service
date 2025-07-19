/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.LegajoControl;

/**
 * @author daniel
 *
 */
@Repository
public interface LegajoControlRepository extends JpaRepository<LegajoControl, Long> {

	public List<LegajoControl> findAllByAnhoAndMes(Integer anho, Integer mes);

	public List<LegajoControl> findAllByAnhoAndMes(Integer anho, Integer mes, Sort sort);

	public List<LegajoControl> findAllByAnhoAndMesAndLiquidadoOrderByLegajoId(Integer anho, Integer mes,
			Byte liquidado);

	public Optional<LegajoControl> findByLegajoControlId(Long legajoControlId);

	public Optional<LegajoControl> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
