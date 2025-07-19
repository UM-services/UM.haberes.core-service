/**
 * 
 */
package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.BonoImpresion;

/**
 * @author daniel
 *
 */
@Repository
public interface BonoImpresionRepository extends JpaRepository<BonoImpresion, Long> {

}
