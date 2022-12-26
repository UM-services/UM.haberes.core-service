/**
 * 
 */
package ar.edu.um.haberes.rest.service.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.dto.FormularioAsignacionCargo;
import ar.edu.um.haberes.rest.service.CategoriaService;
import ar.edu.um.haberes.rest.service.DependenciaService;
import ar.edu.um.haberes.rest.service.FacultadService;

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
