/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.AsignadoCategoria;

/**
 * @author daniel
 *
 */
@Repository
public interface IAsignadoCategoriaRepository extends JpaRepository<AsignadoCategoria, String> {

	public List<AsignadoCategoria> findAllByDependenciaIdAndCategoriaId(Integer dependenciaId, Integer categoriaId,
			Sort sort);

}
