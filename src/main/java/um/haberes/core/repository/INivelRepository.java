/**
 * 
 */
package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.Nivel;

/**
 * @author daniel
 *
 */
@Repository
public interface INivelRepository extends JpaRepository<Nivel, Integer> {

}
