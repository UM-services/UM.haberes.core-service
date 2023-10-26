/**
 * 
 */
package um.haberes.rest.service.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import um.haberes.rest.exception.AcreditacionException;
import um.haberes.rest.exception.view.CargoClasePeriodoNotFoundException;
import um.haberes.rest.kotlin.model.Acreditacion;
import um.haberes.rest.kotlin.model.CargoClase;
import um.haberes.rest.kotlin.model.CargoClaseDetalle;
import um.haberes.rest.kotlin.model.Clase;
import um.haberes.rest.model.CargoClasePeriodo;
import um.haberes.rest.service.AcreditacionService;
import um.haberes.rest.service.CargoClaseDetalleService;
import um.haberes.rest.service.CargoClasePeriodoService;
import um.haberes.rest.service.CargoClaseService;
import um.haberes.rest.service.ClaseService;
import um.haberes.rest.service.view.CargoClaseDetallePeriodoService;
import um.haberes.rest.util.Periodo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class CargoClaseToolService {

	@Autowired
	private ClaseService claseService;

	@Autowired
	private CargoClaseService cargoClaseService;

	@Autowired
	private CargoClaseDetalleService cargoClaseDetalleService;

	@Autowired
	private CargoClaseDetallePeriodoService cargoClaseDetallePeriodoService;

	@Autowired
	private AcreditacionService acreditacionService;

	@Autowired
	private CargoClasePeriodoService cargoClasePeriodoService;

	@Transactional
	public void updateValorHora(Integer anho, Integer mes) {
		Map<Integer, Clase> clases = claseService.findAll().stream()
				.collect(Collectors.toMap(Clase::getClaseId, clase -> clase));

		Map<Long, CargoClase> cargoclases = cargoClaseService.findAll().stream()
				.collect(Collectors.toMap(CargoClase::getCargoClaseId, cargoClase -> cargoClase));
		List<CargoClaseDetalle> detalles = cargoClaseDetalleService.findAllByCargoClaseDetalleIdIn(
				cargoClaseDetallePeriodoService.findAllLiquidadoByPeriodoResto(anho, mes).stream()
						.map(detalle -> detalle.getCargoClaseDetalleId()).collect(Collectors.toList()));

		for (CargoClaseDetalle cargoClaseDetalle : detalles) {
			Clase clase = clases.get(cargoclases.get(cargoClaseDetalle.getCargoClaseId()).getClaseId());
			cargoClaseDetalle.setValorHora(clase.getValorHora());
		}
		detalles = cargoClaseDetalleService.saveAll(detalles);
	}

	@Transactional
	public String deleteCargo(Long cargoClasePeriodoId) {
		List<CargoClaseDetalle> detalles = cargoClaseDetalleService.findAllByCargoClasePeriodo(cargoClasePeriodoId);
		for (CargoClaseDetalle detalle : detalles) {
			try {
				Acreditacion acreditacion = acreditacionService.findByPeriodo(detalle.getAnho(), detalle.getMes());
				if (acreditacion.getAcreditado() == 1) {
					return "ERROR: Hay un Período ACREDITADO entre los LIMITES Indicados . . .";
				}
			} catch (AcreditacionException e) {
			}
		}
		cargoClaseDetalleService.deleteAllByCargoClasePeriodoId(cargoClasePeriodoId);
		cargoClasePeriodoService.delete(cargoClasePeriodoId);
		return "Ok";
	}

	@Transactional
	public String addCargo(CargoClasePeriodo cargoClasePeriodo) {
		// Verifica que no haya ningún período intermedio acreditado
		List<Periodo> periodos = Periodo.makePeriodos(cargoClasePeriodo.getPeriodoDesde(),
				cargoClasePeriodo.getPeriodoHasta());
		for (Periodo periodo : periodos) {
			try {
				Acreditacion acreditacion = acreditacionService.findByPeriodo(periodo.getAnho(), periodo.getMes());
				if (acreditacion.getAcreditado() == 1) {
					return "ERROR: Hay un Período ACREDITADO entre los LIMITES Indicados . . .";
				}
			} catch (AcreditacionException e) {
			}
		}
		// Agrega el cargo con clase
		CargoClase cargoClase = cargoClaseService.findByCargoClaseId(cargoClasePeriodo.getCargoClaseId());
		Clase clase = claseService.findByClaseId(cargoClase.getClaseId());
		cargoClasePeriodo.setValorHora(clase.getValorHora());
		cargoClasePeriodo = cargoClasePeriodoService.add(cargoClasePeriodo);
		// Agrega cada uno de los períodos
		List<CargoClaseDetalle> detalles = new ArrayList<CargoClaseDetalle>();
		for (Periodo periodo : periodos) {
			detalles.add(new CargoClaseDetalle(null, cargoClasePeriodo.getLegajoId(), periodo.getAnho(),
					periodo.getMes(), cargoClasePeriodo.getCargoClaseId(), cargoClasePeriodo.getDependenciaId(),
					cargoClasePeriodo.getFacultadId(), cargoClasePeriodo.getGeograficaId(),
					cargoClasePeriodo.getHoras(), clase.getValorHora(), cargoClasePeriodo.getCargoClasePeriodoId(),
					(byte) 0, null, null, null, null, null, null));
		}
		detalles = cargoClaseDetalleService.saveAll(detalles);
		return "Ok";
	}

	@Transactional
	public String changeHasta(Long cargoClasePeriodoId, Integer anhoHasta, Integer mesHasta) {
		CargoClasePeriodo cargoClasePeriodo = null;
		try {
			cargoClasePeriodo = cargoClasePeriodoService.findByCargoClasePeriodoId(cargoClasePeriodoId);
		} catch (CargoClasePeriodoNotFoundException e) {
			return "ERROR: No EXISTE el Cargo . . .";
		}
		// Retengo el periodo hasta
		Long periodoHastaSaved = cargoClasePeriodo.getPeriodoHasta();
		List<Periodo> nuevos = Periodo.makePeriodos(Periodo.nextPeriodo(periodoHastaSaved).toLong(),
				new Periodo(anhoHasta, mesHasta).toLong());
		// Verifica si se puede modificar el periodo
		Long periodoDesde2Delete = Periodo.nextMonth(anhoHasta, mesHasta).toLong();
		List<CargoClaseDetalle> detalles = cargoClaseDetalleService.findAllByCargoClasePeriodo(cargoClasePeriodoId);
		for (CargoClaseDetalle detalle : detalles) {
			// Verifico si el periodo es borrable
			Long periodo = new Periodo(detalle.getAnho(), detalle.getMes()).toLong();
			if (periodoDesde2Delete <= periodo && periodoHastaSaved >= periodo) {
				try {
					Acreditacion acreditacion = acreditacionService.findByPeriodo(detalle.getAnho(), detalle.getMes());
					if (acreditacion.getAcreditado() == 1) {
						return "ERROR: Hay un Período ACREDITADO entre los LIMITES Indicados . . .";
					}
				} catch (AcreditacionException e) {
				}
			}
		}
		for (Periodo periodo : nuevos) {
			try {
				Acreditacion acreditacion = acreditacionService.findByPeriodo(periodo.getAnho(), periodo.getMes());
				log.debug("Acreditacion -> {}", acreditacion);
				if (acreditacion.getAcreditado() == 1) {
					return "ERROR: Hay un Período ACREDITADO entre los LIMITES Indicados . . .";
				}
			} catch (AcreditacionException e) {
			}
		}
		// Pongo el nuevo periodo hasta
		cargoClasePeriodo.setPeriodoHasta(new Periodo(anhoHasta, mesHasta).toLong());
		cargoClasePeriodo = cargoClasePeriodoService.update(cargoClasePeriodo, cargoClasePeriodoId);
		CargoClase cargoClase = cargoClaseService.findByCargoClaseId(cargoClasePeriodo.getCargoClaseId());
		Clase clase = claseService.findByClaseId(cargoClase.getClaseId());
		// Elimina los periodos borrables
		for (CargoClaseDetalle detalle : detalles) {
			// Verifico si el periodo es borrable
			Long periodo = new Periodo(detalle.getAnho(), detalle.getMes()).toLong();
			if (periodoDesde2Delete <= periodo && periodoHastaSaved >= periodo) {
				cargoClaseDetalleService.delete(detalle.getCargoClaseDetalleId());
			}
		}
		// Agrega los períodos nuevos
		detalles = new ArrayList<CargoClaseDetalle>();
		for (Periodo periodo : nuevos) {
			detalles.add(new CargoClaseDetalle(null, cargoClasePeriodo.getLegajoId(), periodo.getAnho(),
					periodo.getMes(), cargoClasePeriodo.getCargoClaseId(), cargoClasePeriodo.getDependenciaId(),
					cargoClasePeriodo.getFacultadId(), cargoClasePeriodo.getGeograficaId(),
					cargoClasePeriodo.getHoras(), clase.getValorHora(), cargoClasePeriodoId, (byte) 0, null, null, null,
					null, null, null));
		}
		detalles = cargoClaseDetalleService.saveAll(detalles);

		return "Ok";
	}

}
