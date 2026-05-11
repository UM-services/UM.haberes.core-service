/**
 * 
 */
package um.haberes.core.service.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.dto.FormularioAsignacionCargo;
import um.haberes.core.service.CategoriaService;
import um.haberes.core.service.DependenciaService;
import um.haberes.core.hexagonal.facultad.application.service.FacultadService;

/**
 * @author daniel
 *
 */
@Service
@RequiredArgsConstructor
public class FormularioAsignacionCargoService {

	private final CategoriaService categoriaService;
	private final DependenciaService dependenciaService;
	private final FacultadService facultadService;

	public FormularioAsignacionCargo findData() {
		return new FormularioAsignacionCargo(categoriaService.findAll(), categoriaService.findAllAsignables(),
				dependenciaService.findAll(), facultadService.getAllFacultades());
	}

}
