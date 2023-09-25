/**
 * 
 */
package um.haberes.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.rest.kotlin.model.Clase;

/**
 * @author daniel
 *
 */
public interface IClaseRepository extends JpaRepository<Clase, Integer> {

	public Optional<Clase> findTopByOrderByClaseIdDesc();

	public Optional<Clase> findByClaseId(Integer claseId);
}
