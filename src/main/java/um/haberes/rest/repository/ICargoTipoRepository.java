/**
 * 
 */
package um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.CargoTipo;

/**
 * @author daniel
 *
 */
@Repository
public interface ICargoTipoRepository extends JpaRepository<CargoTipo, Integer> {

	public List<CargoTipo> findAllByCargoTipoIdIn(List<Integer> cargoTipoIds);

	public Optional<CargoTipo> findByCargoTipoId(Integer cargoTipoId);

}
