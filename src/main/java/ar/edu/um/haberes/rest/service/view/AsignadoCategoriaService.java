/**
 * 
 */
package ar.edu.um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.view.AsignadoCategoria;
import ar.edu.um.haberes.rest.repository.view.IAsignadoCategoriaRepository;

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
