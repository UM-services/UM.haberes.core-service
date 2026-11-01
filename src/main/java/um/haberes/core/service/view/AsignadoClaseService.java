/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.core.model.view.AsignadoClase;
import um.haberes.core.repository.view.JpaAsignadoClaseRepository;

/**
 * @author daniel
 *
 */
@Service
public class AsignadoClaseService {

	@Autowired
	private JpaAsignadoClaseRepository repository;

	public List<AsignadoClase> findAllAsignados(Integer dependenciaId, Long cargoClaseId) {
		return repository.findAllByDependenciaIdAndCargoClaseId(dependenciaId, cargoClaseId,
				Sort.by("periodoHasta").descending().and(Sort.by("legajoId")));
	}

}
