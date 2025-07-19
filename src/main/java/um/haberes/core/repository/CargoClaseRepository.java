/**
 * 
 */
package um.haberes.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.CargoClase;

/**
 * @author daniel
 *
 */
@Repository
public interface CargoClaseRepository extends JpaRepository<CargoClase, Long> {

	public Optional<CargoClase> findByCargoClaseId(Long cargoClaseId);

}
