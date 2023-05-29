/**
 * 
 */
package um.haberes.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.ControlNotFoundException;
import um.haberes.rest.model.Control;
import um.haberes.rest.repository.IControlRepository;

/**
 * @author daniel
 *
 */
@Service
public class ControlService {

	@Autowired
	private IControlRepository repository;

	public Control findByPeriodo(Integer anho, Integer mes) {
		return repository.findByAnhoAndMes(anho, mes).orElseThrow(() -> new ControlNotFoundException(anho, mes));
	}

	public Control add(Control control) {
		repository.save(control);
		return control;
	}

	public Control update(Control newControl, Long controlId) {
		return repository.findByControlId(controlId).map(control -> {
			control = new Control(controlId, newControl.getAnho(), newControl.getMes(), newControl.getFechaDesde(),
					newControl.getFechaHasta(), newControl.getFechaPago(), newControl.getAporteJubilatorio(),
					newControl.getDepositoBanco(), newControl.getFechaDeposito(), newControl.getDoctorado(),
					newControl.getMaestria(), newControl.getEspecializacion(), newControl.getFamiliaNumerosa(),
					newControl.getEscuelaPrimaria(), newControl.getEscuelaSecundaria(),
					newControl.getEscuelaPrimariaNumerosa(), newControl.getEscuelaSecundariaNumerosa(),
					newControl.getPrenatal(), newControl.getLibre(), newControl.getAyudaEscolar(),
					newControl.getMatrimonio(), newControl.getNacimiento(), newControl.getFuncionDireccion(),
					newControl.getMayorResponsabilidadPatrimonial(), newControl.getPolimedb(), newControl.getPolimedo(),
					newControl.getMontoeci(), newControl.getValampo(), newControl.getJubilaem(),
					newControl.getInssjpem(), newControl.getOsociaem(), newControl.getJubilpat(),
					newControl.getInssjpat(), newControl.getOsocipat(), newControl.getAnsalpat(),
					newControl.getSalfapat(), newControl.getMinimoAporte(), newControl.getMaximoAporte(),
					newControl.getMincontr(), newControl.getMaximo1sijp(), newControl.getMaximo2sijp(),
					newControl.getMaximo3sijp(), newControl.getMaximo4sijp(), newControl.getMaximo5sijp(),
					newControl.getEstadoDocenteTitular(), newControl.getEstadoDocenteAdjunto(),
					newControl.getEstadoDocenteAuxiliar());
			repository.save(control);
			return control;
		}).orElseThrow(() -> new ControlNotFoundException(controlId));
	}

}
