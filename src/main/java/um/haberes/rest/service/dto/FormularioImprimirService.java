/**
 * 
 */
package um.haberes.rest.service.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.kotlin.model.LegajoControl;
import um.haberes.rest.kotlin.model.dto.FormularioImprimir;
import um.haberes.rest.service.ContactoService;
import um.haberes.rest.service.LegajoControlService;

/**
 * @author daniel
 *
 */
@Service
public class FormularioImprimirService {

	@Autowired
	private LegajoControlService legajoControlService;

	@Autowired
	private ContactoService contactoService;

	public FormularioImprimir findData(Integer anho, Integer mes, Integer dependenciaId, String filtro) {
		List<LegajoControl> legajoControls = legajoControlService.findAllDependenciaByPeriodo(anho, mes, dependenciaId,
				filtro);
		return new FormularioImprimir(legajoControls, contactoService.findAllLegajos(legajoControls.stream()
				.map(legajoControl -> legajoControl.getLegajoId()).collect(Collectors.toList())));
	}

}
