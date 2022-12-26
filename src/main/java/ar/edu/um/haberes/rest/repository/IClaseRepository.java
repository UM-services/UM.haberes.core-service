/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.um.haberes.rest.model.Clase;

/**
 * @author daniel
 *
 */
public interface IClaseRepository extends JpaRepository<Clase, Integer> {

	public Optional<Clase> findTopByOrderByClaseIdDesc();

	public Optional<Clase> findByClaseId(Integer claseId);
}
