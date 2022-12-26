/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.view.AsignadoCategoria;
import ar.edu.um.haberes.rest.model.view.pk.AsignadoCategoriaPk;

/**
 * @author daniel
 *
 */
@Repository
public interface IAsignadoCategoriaRepository extends JpaRepository<AsignadoCategoria, AsignadoCategoriaPk> {

	public List<AsignadoCategoria> findAllByDependenciaIdAndCategoriaId(Integer dependenciaId, Integer categoriaId,
			Sort sort);

}
