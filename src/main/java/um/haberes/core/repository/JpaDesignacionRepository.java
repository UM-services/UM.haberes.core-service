/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.DesignacionEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaDesignacionRepository extends JpaRepository<DesignacionEntity, Integer> {

	public List<DesignacionEntity> findAllByCategoriaIdNotNull();

	public Optional<DesignacionEntity> findByDesignacionTipoIdAndCargoTipoIdAndAnualAndSemestral(Integer designaciontipoId,
			Integer cargotipoId, Byte anual, Byte semestral);

}
