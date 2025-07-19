/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.AsignadoClase;

/**
 * @author daniel
 *
 */
@Repository
public interface AsignadoClaseRepository extends JpaRepository<AsignadoClase, String> {

	public List<AsignadoClase> findAllByDependenciaIdAndCargoClaseId(Integer dependenciaId, Long cargoClaseId,
			Sort sort);

}
