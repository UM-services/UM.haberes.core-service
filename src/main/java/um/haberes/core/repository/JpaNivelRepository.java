/**
 * 
 */
package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.NivelEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaNivelRepository extends JpaRepository<NivelEntity, Integer> {

}
