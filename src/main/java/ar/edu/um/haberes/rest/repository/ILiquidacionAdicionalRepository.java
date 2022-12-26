/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.LiquidacionAdicional;

/**
 * @author daniel
 *
 */
@Repository
public interface ILiquidacionAdicionalRepository extends JpaRepository<LiquidacionAdicional, Long> {

	public List<LiquidacionAdicional> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	@Modifying
	public void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
