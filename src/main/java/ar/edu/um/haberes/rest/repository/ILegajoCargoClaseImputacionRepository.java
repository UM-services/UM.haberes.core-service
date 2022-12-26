/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.LegajoCargoClaseImputacion;

/**
 * @author daniel
 *
 */
@Repository
public interface ILegajoCargoClaseImputacionRepository extends JpaRepository<LegajoCargoClaseImputacion, Long> {

	public List<LegajoCargoClaseImputacion> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	@Modifying
	public void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	@Modifying
	public void deleteAllByAnhoAndMes(Integer anho, Integer mes);

}
