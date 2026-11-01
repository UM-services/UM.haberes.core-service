/**
 * 
 */
package um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.persistence.entity.CargoTipoEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCargoTipoRepository extends JpaRepository<CargoTipoEntity, Integer> {

	public List<CargoTipoEntity> findAllByCargoTipoIdIn(List<Integer> cargoTipoIds);

	public Optional<CargoTipoEntity> findByCargoTipoId(Integer cargoTipoId);

}
