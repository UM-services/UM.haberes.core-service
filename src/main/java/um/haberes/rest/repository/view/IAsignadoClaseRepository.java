/**
 * 
 */
package um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.rest.kotlin.view.AsignadoClase;

/**
 * @author daniel
 *
 */
@Repository
public interface IAsignadoClaseRepository extends JpaRepository<AsignadoClase, String> {

	public List<AsignadoClase> findAllByDependenciaIdAndCargoClaseId(Integer dependenciaId, Long cargoClaseId,
			Sort sort);

}
