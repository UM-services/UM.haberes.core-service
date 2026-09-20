/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.LiquidacionAdicionalEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaLiquidacionAdicionalRepository extends JpaRepository<LiquidacionAdicionalEntity, Long> {

	public List<LiquidacionAdicionalEntity> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	public Optional<LiquidacionAdicionalEntity> findByLegajoIdAndAnhoAndMesAndDependenciaId(Long legajoId, Integer anho, Integer mes, Integer dependenciaId);

	@Modifying
	public void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
