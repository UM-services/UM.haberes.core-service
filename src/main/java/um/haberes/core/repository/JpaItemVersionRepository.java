/**
 * 
 */
package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.ItemVersionEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaItemVersionRepository extends JpaRepository<ItemVersionEntity, Long> {

}
