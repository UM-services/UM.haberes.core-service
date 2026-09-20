/**
 * 
 */
package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.LiquidacionVersionEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaLiquidacionVersionRepository extends JpaRepository<LiquidacionVersionEntity, Long>{

}
