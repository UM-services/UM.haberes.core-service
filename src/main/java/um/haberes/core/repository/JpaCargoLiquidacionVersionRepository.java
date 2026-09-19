/**
 * 
 */
package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.model.CargoLiquidacionVersionEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCargoLiquidacionVersionRepository extends JpaRepository<CargoLiquidacionVersionEntity, Long> {

}
