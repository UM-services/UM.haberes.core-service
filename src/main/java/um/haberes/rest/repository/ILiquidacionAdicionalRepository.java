/**
 * 
 */
package um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.rest.kotlin.model.LiquidacionAdicional;

/**
 * @author daniel
 *
 */
@Repository
public interface ILiquidacionAdicionalRepository extends JpaRepository<LiquidacionAdicional, Long> {

	public List<LiquidacionAdicional> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	public Optional<LiquidacionAdicional> findByLegajoIdAndAnhoAndMesAndDependenciaId(Long legajoId, Integer anho, Integer mes, Integer dependenciaId);

	@Modifying
	public void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
