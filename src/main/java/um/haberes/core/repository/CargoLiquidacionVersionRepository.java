/**
 * 
 */
package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.kotlin.model.CargoLiquidacionVersion;

/**
 * @author daniel
 *
 */
@Repository
public interface CargoLiquidacionVersionRepository extends JpaRepository<CargoLiquidacionVersion, Long> {

}
