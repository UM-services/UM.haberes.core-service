/**
 * 
 */
package um.haberes.core.service.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import um.haberes.core.model.dto.FormularioAsignacionCargo;
import um.haberes.core.hexagonal.liquidaciones.categoria.application.service.CategoriaService;
import um.haberes.core.hexagonal.personas.dependencia.application.service.DependenciaService;
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
