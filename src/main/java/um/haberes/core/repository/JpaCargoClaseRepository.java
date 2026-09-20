/**
 * 
 */
package um.haberes.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.CargoClaseEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCargoClaseRepository extends JpaRepository<CargoClaseEntity, Long> {

	public Optional<CargoClaseEntity> findByCargoClaseId(Long cargoClaseId);

}
