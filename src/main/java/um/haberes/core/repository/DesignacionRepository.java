/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.Designacion;

/**
 * @author daniel
 *
 */
@Repository
public interface DesignacionRepository extends JpaRepository<Designacion, Integer> {

	public List<Designacion> findAllByCategoriaIdNotNull();

	public Optional<Designacion> findByDesignacionTipoIdAndCargoTipoIdAndAnualAndSemestral(Integer designaciontipoId,
			Integer cargotipoId, Byte anual, Byte semestral);

}
