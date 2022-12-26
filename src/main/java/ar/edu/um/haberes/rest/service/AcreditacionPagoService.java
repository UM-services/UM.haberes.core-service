/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.AcreditacionPagoNotFoundException;
import ar.edu.um.haberes.rest.model.AcreditacionPago;
import ar.edu.um.haberes.rest.repository.IAcreditacionPagoRepository;

/**
 * @author daniel
 *
 */
@Service
public class AcreditacionPagoService {
	@Autowired
	private IAcreditacionPagoRepository repository;

	public AcreditacionPago findByUnique(Integer anho, Integer mes, OffsetDateTime fechaPago) {
		return repository.findByAnhoAndMesAndFechaPago(anho, mes, fechaPago)
				.orElseThrow(() -> new AcreditacionPagoNotFoundException(anho, mes, fechaPago));
	}

	public AcreditacionPago add(AcreditacionPago acreditacionPago) {
		repository.save(acreditacionPago);
		return acreditacionPago;
	}

	public AcreditacionPago update(AcreditacionPago newAcreditacionPago, Long acreditacionPagoId) {
		return repository.findById(acreditacionPagoId).map(acreditacionPago -> {
			acreditacionPago.setAnho(newAcreditacionPago.getAnho());
			acreditacionPago.setMes(newAcreditacionPago.getMes());
			acreditacionPago.setFechaPago(newAcreditacionPago.getFechaPago());
			acreditacionPago.setTotalSantander(newAcreditacionPago.getTotalSantander());
			acreditacionPago.setTotalOtrosBancos(newAcreditacionPago.getTotalOtrosBancos());
			acreditacionPago.setComprobanteIdPago(newAcreditacionPago.getComprobanteIdPago());
			acreditacionPago.setPuntoVentaPago(newAcreditacionPago.getPuntoVentaPago());
			acreditacionPago.setNumeroComprobantePago(newAcreditacionPago.getNumeroComprobantePago());
			return repository.save(acreditacionPago);
		}).orElseThrow(() -> new AcreditacionPagoNotFoundException(acreditacionPagoId));
	}
}
