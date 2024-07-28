/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.Actividad;

/**
 * @author daniel
 *
 */
@Repository
public interface IActividadRepository extends JpaRepository<Actividad, Long> {

	public List<Actividad> findAllByLegajoId(Long legajoId, Sort sort);

	public Optional<Actividad> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
