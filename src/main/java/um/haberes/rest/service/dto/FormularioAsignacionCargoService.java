/**
 * 
 */
package um.haberes.rest.service.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.kotlin.model.dto.FormularioAsignacionCargo;
import um.haberes.rest.service.CategoriaService;
import um.haberes.rest.service.DependenciaService;
import um.haberes.rest.service.FacultadService;

/**
 * @author daniel
 *
 */
@Service
public class FormularioAsignacionCargoService {

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private DependenciaService dependenciaService;

	@Autowired
	private FacultadService facultadService;

	public FormularioAsignacionCargo findData() {
		return new FormularioAsignacionCargo(categoriaService.findAll(), categoriaService.findAllAsignables(),
				dependenciaService.findAll(), facultadService.findAllFacultades());
	}

}
