/**
 * 
 */
package um.haberes.core.service.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.exception.AcreditacionException;
import um.haberes.core.exception.view.CargoClasePeriodoException;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.persistence.entity.AcreditacionEntity;
import um.haberes.core.model.*;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.service.AcreditacionService;
import um.haberes.core.service.CargoClaseDetalleService;
import um.haberes.core.service.CargoClasePeriodoService;
import um.haberes.core.service.CargoClaseService;
import um.haberes.core.service.ClaseService;
import um.haberes.core.service.view.CargoClaseDetallePeriodoService;
import um.haberes.core.util.Periodo;
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
		Map<Integer, ClaseEntity> clases = claseService.findAll().stream()
				.collect(Collectors.toMap(ClaseEntity::getClaseId, clase -> clase));

		Map<Long, CargoClaseEntity> cargoclases = cargoClaseService.findAll().stream()
				.collect(Collectors.toMap(CargoClaseEntity::getCargoClaseId, cargoClase -> cargoClase));
		List<CargoClaseDetalleEntity> detalles = cargoClaseDetalleService.findAllByCargoClaseDetalleIdIn(
				cargoClaseDetallePeriodoService.findAllLiquidadoByPeriodoResto(anho, mes).stream()
						.map(detalle -> detalle.getCargoClaseDetalleId()).collect(Collectors.toList()));

		for (CargoClaseDetalleEntity cargoClaseDetalle : detalles) {
			ClaseEntity clase = clases.get(cargoclases.get(cargoClaseDetalle.getCargoClaseId()).getClaseId());
			cargoClaseDetalle.setValorHora(clase.getValorHora());
		}
		detalles = cargoClaseDetalleService.saveAll(detalles);
	}

	@Transactional
	public String deleteCargo(Long cargoClasePeriodoId) {
		List<CargoClaseDetalleEntity> detalles = cargoClaseDetalleService.findAllByCargoClasePeriodo(cargoClasePeriodoId);
		for (CargoClaseDetalleEntity detalle : detalles) {
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
	public String addCargo(CargoClasePeriodoEntity cargoClasePeriodo) {
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
				log.debug("No hay acreditacion");
			}
		}
		// Agrega el cargo con clase
		CargoClaseEntity cargoClase = cargoClaseService.findByCargoClaseId(cargoClasePeriodo.getCargoClaseId());
		ClaseEntity clase = claseService.findByClaseId(cargoClase.getClaseId());
		cargoClasePeriodo.setValorHora(clase.getValorHora());
		cargoClasePeriodo = cargoClasePeriodoService.add(cargoClasePeriodo);
		// Agrega cada uno de los períodos
		List<CargoClaseDetalleEntity> detalles = new ArrayList<CargoClaseDetalleEntity>();
		for (Periodo periodo : periodos) {
			detalles.add(new CargoClaseDetalleEntity(
					null,
					cargoClasePeriodo.getLegajoId(),
					periodo.getAnho(),
					periodo.getMes(),
					cargoClasePeriodo.getCargoClaseId(),
					cargoClasePeriodo.getDependenciaId(),
					cargoClasePeriodo.getFacultadId(),
					cargoClasePeriodo.getGeograficaId(),
					cargoClasePeriodo.getHoras(),
					clase.getValorHora(),
					cargoClasePeriodo.getAplicaAdicional(),
					cargoClasePeriodo.getCargoClasePeriodoId(),
					(byte) 0,
					null,
					null,
					null,
					null,
					null,
					null
					)
			);
		}
		detalles = cargoClaseDetalleService.saveAll(detalles);
		return "Ok";
	}

	@Transactional
	public String changeHasta(Long cargoClasePeriodoId, Integer anhoHasta, Integer mesHasta) {
		CargoClasePeriodoEntity cargoClasePeriodo = null;
		try {
			cargoClasePeriodo = cargoClasePeriodoService.findByCargoClasePeriodoId(cargoClasePeriodoId);
		} catch (CargoClasePeriodoException e) {
			return "ERROR: No EXISTE el CargoEntity . . .";
		}
		// Retengo el periodo hasta
		Long periodoHastaSaved = cargoClasePeriodo.getPeriodoHasta();
		List<Periodo> nuevos = Periodo.makePeriodos(Periodo.nextPeriodo(periodoHastaSaved).toLong(),
				Periodo.builder().anho(anhoHasta).mes(mesHasta).build().toLong());
		// Verifica si se puede modificar el periodo
		Long periodoDesde2Delete = Periodo.nextMonth(anhoHasta, mesHasta).toLong();
		List<CargoClaseDetalleEntity> detalles = cargoClaseDetalleService.findAllByCargoClasePeriodo(cargoClasePeriodoId);
		for (CargoClaseDetalleEntity detalle : detalles) {
			// Verifico si el periodo es borrable
			Long periodo = Periodo.builder().anho(detalle.getAnho()).mes(detalle.getMes()).build().toLong();
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
				log.debug("AcreditacionEntity -> {}", acreditacion);
				if (acreditacion.getAcreditado() == 1) {
					return "ERROR: Hay un Período ACREDITADO entre los LIMITES Indicados . . .";
				}
			} catch (AcreditacionException e) {
			}
		}
		// Pongo el nuevo periodo hasta
		cargoClasePeriodo.setPeriodoHasta(Periodo.builder().anho(anhoHasta).mes(mesHasta).build().toLong());
		cargoClasePeriodo = cargoClasePeriodoService.update(cargoClasePeriodo, cargoClasePeriodoId);
		CargoClaseEntity cargoClase = cargoClaseService.findByCargoClaseId(cargoClasePeriodo.getCargoClaseId());
		ClaseEntity clase = claseService.findByClaseId(cargoClase.getClaseId());
		// Elimina los periodos borrables
		for (CargoClaseDetalleEntity detalle : detalles) {
			// Verifico si el periodo es borrable
			Long periodo = Periodo.builder().anho(detalle.getAnho()).mes(detalle.getMes()).build().toLong();
			if (periodoDesde2Delete <= periodo && periodoHastaSaved >= periodo) {
				cargoClaseDetalleService.delete(detalle.getCargoClaseDetalleId());
			}
		}
		// Agrega los períodos nuevos
		detalles = new ArrayList<CargoClaseDetalleEntity>();
		for (Periodo periodo : nuevos) {
			detalles.add(new CargoClaseDetalleEntity(
					null,
					cargoClasePeriodo.getLegajoId(),
					periodo.getAnho(),
					periodo.getMes(),
					cargoClasePeriodo.getCargoClaseId(),
					cargoClasePeriodo.getDependenciaId(),
					cargoClasePeriodo.getFacultadId(),
					cargoClasePeriodo.getGeograficaId(),
					cargoClasePeriodo.getHoras(),
					clase.getValorHora(),
					cargoClasePeriodo.getAplicaAdicional(),
					cargoClasePeriodoId,
					(byte) 0,
					null,
					null,
					null,
					null,
					null,
					null
			)
			);
		}
		detalles = cargoClaseDetalleService.saveAll(detalles);

		return "Ok";
	}

}
