/**
 * 
 */
package um.haberes.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.rest.kotlin.model.Situacion;

/**
 * @author daniel
 *
 */
public interface ISituacionRepository extends JpaRepository<Situacion, Integer> {

}
