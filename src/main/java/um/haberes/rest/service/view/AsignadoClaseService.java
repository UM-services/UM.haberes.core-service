/**
 * 
 */
package um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.model.view.AsignadoClase;
import um.haberes.rest.repository.view.IAsignadoClaseRepository;

/**
 * @author daniel
 *
 */
@Service
public class AsignadoClaseService {

	@Autowired
	private IAsignadoClaseRepository repository;

	public List<AsignadoClase> findAllAsignados(Integer dependenciaId, Long cargoClaseId) {
		return repository.findAllByDependenciaIdAndCargoClaseId(dependenciaId, cargoClaseId,
				Sort.by("periodoHasta").descending().and(Sort.by("legajoId")));
	}

}
