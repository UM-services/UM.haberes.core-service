/**
 * 
 */
package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.ItemVersion;

/**
 * @author daniel
 *
 */
@Repository
public interface IItemVersionRepository extends JpaRepository<ItemVersion, Long> {

}
