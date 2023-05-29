/**
 * 
 */
package um.haberes.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.CargoClase;

/**
 * @author daniel
 *
 */
@Repository
public interface ICargoClaseRepository extends JpaRepository<CargoClase, Long> {

	public Optional<CargoClase> findByCargoClaseId(Long cargoClaseId);

}
