/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.LegajoCategoriaImputacion;

/**
 * @author daniel
 *
 */
@Repository
public interface LegajoCategoriaImputacionRepository extends JpaRepository<LegajoCategoriaImputacion, Long> {

	List<LegajoCategoriaImputacion> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	@Modifying
	void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	@Modifying
	void deleteAllByAnhoAndMes(Integer anho, Integer mes);

}
