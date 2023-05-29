/**
 * 
 */
package um.haberes.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.LegajoCodigoImputacion;

/**
 * @author daniel
 *
 */
@Repository
public interface ILegajoCodigoImputacionRepository extends JpaRepository<LegajoCodigoImputacion, Long> {

	public List<LegajoCodigoImputacion> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	@Modifying
	public void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	@Modifying
	public void deleteAllByAnhoAndMes(Integer anho, Integer mes);

}
