/**
 * 
 */
package um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.kotlin.view.AsignadoCategoria;
import um.haberes.rest.repository.view.IAsignadoCategoriaRepository;

/**
 * @author daniel
 *
 */
@Service
public class AsignadoCategoriaService {

	@Autowired
	private IAsignadoCategoriaRepository repository;

	public List<AsignadoCategoria> findAllAsignados(Integer dependenciaId, Integer categoriaId) {
		return repository.findAllByDependenciaIdAndCategoriaId(dependenciaId, categoriaId,
				Sort.by("periodoHasta").descending().and(Sort.by("legajoId")));
	}

}
