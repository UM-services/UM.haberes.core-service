/**
 * 
 */
package um.haberes.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.rest.kotlin.model.CargoLiquidacionVersion;

/**
 * @author daniel
 *
 */
@Repository
public interface ICargoLiquidacionVersionRepository extends JpaRepository<CargoLiquidacionVersion, Long> {

}
