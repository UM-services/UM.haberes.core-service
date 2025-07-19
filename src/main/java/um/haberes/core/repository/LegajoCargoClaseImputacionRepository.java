/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.LegajoCargoClaseImputacion;

/**
 * @author daniel
 *
 */
@Repository
public interface LegajoCargoClaseImputacionRepository extends JpaRepository<LegajoCargoClaseImputacion, Long> {

	List<LegajoCargoClaseImputacion> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	@Modifying
	void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	@Modifying
	void deleteAllByAnhoAndMes(Integer anho, Integer mes);

}
