/**
 * 
 */
package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.LiquidacionVersion;

/**
 * @author daniel
 *
 */
@Repository
public interface LiquidacionVersionRepository extends JpaRepository<LiquidacionVersion, Long>{

}
