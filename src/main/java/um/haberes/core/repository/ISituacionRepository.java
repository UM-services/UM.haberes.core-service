/**
 * 
 */
package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.kotlin.model.Situacion;

/**
 * @author daniel
 *
 */
public interface ISituacionRepository extends JpaRepository<Situacion, Integer> {

}
