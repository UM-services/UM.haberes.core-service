/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.ActividadEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaActividadRepository extends JpaRepository<ActividadEntity, Long> {

	public List<ActividadEntity> findAllByLegajoId(Long legajoId, Sort sort);

	public Optional<ActividadEntity> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
