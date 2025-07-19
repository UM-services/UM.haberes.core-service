/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.LegajoCodigoImputacion;

/**
 * @author daniel
 *
 */
@Repository
public interface LegajoCodigoImputacionRepository extends JpaRepository<LegajoCodigoImputacion, Long> {

	List<LegajoCodigoImputacion> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	List<LegajoCodigoImputacion> findAllByLegajoIdAndAnhoAndMesAndCodigoIdIn(Long legajoId, Integer anho, Integer mes, List<Integer> codigoIds);

	@Modifying
	void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	@Modifying
	void deleteAllByAnhoAndMes(Integer anho, Integer mes);

}
