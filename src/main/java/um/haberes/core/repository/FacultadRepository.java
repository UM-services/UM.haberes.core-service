/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.Facultad;

/**
 * @author daniel
 *
 */
@Repository
public interface FacultadRepository extends JpaRepository<Facultad, Integer> {

	public List<Facultad> findAllByFacultadIdIn(List<Integer> ids);

	public Optional<Facultad> findByFacultadId(Integer facultadId);

}
