/**
 * 
 */
package um.haberes.rest.service.facade;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.ActividadNotFoundException;
import um.haberes.rest.exception.AdicionalCursoTablaNotFoundException;
import um.haberes.rest.exception.CategoriaPeriodoNotFoundException;
import um.haberes.rest.exception.LegajoControlNotFoundException;
import um.haberes.rest.exception.LetraNotFoundException;
import um.haberes.rest.exception.LiquidacionException;
import um.haberes.rest.kotlin.model.*;
import um.haberes.rest.model.Actividad;
import um.haberes.rest.model.AdicionalCursoRango;
import um.haberes.rest.model.AdicionalCursoTabla;
import um.haberes.rest.model.Cargo;
import um.haberes.rest.model.CargoClaseDetalle;
import um.haberes.rest.model.Categoria;
import um.haberes.rest.model.CategoriaPeriodo;
import um.haberes.rest.model.Control;
import um.haberes.rest.model.CursoCargo;
import um.haberes.rest.model.CursoDesarraigo;
import um.haberes.rest.model.CursoFusion;
import um.haberes.rest.model.Item;
import um.haberes.rest.model.LegajoControl;
import um.haberes.rest.model.Letra;
import um.haberes.rest.model.LiquidacionAdicional;
import um.haberes.rest.model.Novedad;
import um.haberes.rest.model.Persona;
import um.haberes.rest.model.view.NovedadDuplicada;
import um.haberes.rest.service.ActividadService;
import um.haberes.rest.service.AdicionalCursoTablaService;
import um.haberes.rest.service.AntiguedadService;
import um.haberes.rest.service.CargoClaseDetalleService;
import um.haberes.rest.service.CargoLiquidacionService;
import um.haberes.rest.service.CargoService;
import um.haberes.rest.service.CategoriaPeriodoService;
import um.haberes.rest.service.CategoriaService;
import um.haberes.rest.service.CodigoService;
import um.haberes.rest.service.CodigoGrupoService;
import um.haberes.rest.service.ControlService;
import um.haberes.rest.service.CursoCargoService;
import um.haberes.rest.service.CursoDesarraigoService;
import um.haberes.rest.service.CursoFusionService;
import um.haberes.rest.service.DependenciaService;
import um.haberes.rest.service.ItemService;
import um.haberes.rest.service.LegajoControlService;
import um.haberes.rest.service.LetraService;
import um.haberes.rest.service.LiquidacionAdicionalService;
import um.haberes.rest.service.LiquidacionService;
import um.haberes.rest.service.NovedadService;
import um.haberes.rest.service.PersonaService;
import um.haberes.rest.service.view.NovedadDuplicadaService;
import um.haberes.rest.util.Periodo;
import um.haberes.rest.util.Tool;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class MakeLiquidacionService {

	@Autowired
	private CargoLiquidacionService cargoLiquidacionService;

	@Autowired
	private DesignacionToolService designacionToolService;

	@Autowired
	private CargoClaseDetalleService cargoClaseDetalleService;

	@Autowired
	private ControlService controlService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private NovedadService novedadService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private LegajoControlService legajoControlService;

	@Autowired
	private CodigoGrupoService codigoGrupoService;

	@Autowired
	private CursoDesarraigoService cursoDesarraigoService;

	@Autowired
	private CursoFusionService cursoFusionService;

	@Autowired
	private DependenciaService dependenciaService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private AntiguedadService antiguedadService;

	@Autowired
	private LiquidacionService liquidacionService;

	@Autowired
	private ActividadService actividadService;

	@Autowired
	private LetraService letraService;

	@Autowired
	private CargoService cargoService;

	@Autowired
	private NovedadDuplicadaService novedadDuplicadaService;

	@Autowired
	private CursoCargoService cursoCargoService;

	@Autowired
	private CodigoService codigoService;

	@Autowired
	private AdicionalCursoTablaService adicionalCursoTablaService;

	@Autowired
	private CategoriaPeriodoService categoriaPeriodoService;

	@Autowired
	private LiquidacionAdicionalService liquidacionAdicionalService;

	private Control control;
	private Persona persona;
	private LegajoControl legajoControl;
	private List<BigDecimal> indices;
	private Map<Integer, Codigo> codigos;
	private Map<Integer, Item> items;
	private Map<Integer, Novedad> novedades;

	private void makeContext(Long legajoId, Integer anho, Integer mes) {
		this.deleteNovedadDuplicada(anho, mes);
		indices = designacionToolService.indiceAntiguedad(legajoId, anho, mes);
		persona = personaService.findByLegajoId(legajoId);
		control = controlService.findByPeriodo(anho, mes);
		legajoControl = null;
		try {
			legajoControl = legajoControlService.findByUnique(legajoId, anho, mes);
		} catch (LegajoControlNotFoundException e) {
			legajoControl = new LegajoControl(null, legajoId, anho, mes, (byte) 0, (byte) 0, (byte) 0, null);
			legajoControl = legajoControlService.add(legajoControl);
		}
		codigos = codigoService.findAll().stream().collect(Collectors.toMap(Codigo::getCodigoId, codigo -> codigo));
		novedades = novedadService.findAllByLegajo(legajoId, anho, mes).stream()
				.filter(novedad -> novedad.getCodigoId() != 980)
				.collect(Collectors.toMap(Novedad::getCodigoId, novedad -> novedad));
		items = new HashMap<Integer, Item>();
	}

	@Transactional
	public List<Item> basicoAndAntiguedad(Long legajoId, Integer anho, Integer mes) {
		makeContext(legajoId, anho, mes);
		calculaBasicoAndAntiguedad(legajoId, anho, mes);
		return new ArrayList<>(items.values());
	}

	@Transactional
	public void liquidacionByLegajoId(Long legajoId, Integer anho, Integer mes, Boolean force) {
		// Verifica si ya fue acreditado el período para ver si se puede liquidar
		if (!force) {
			try {
				Liquidacion liquidacion = liquidacionService.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
				if (liquidacion.getBloqueado() == (byte) 1) {
					return;
				}
			} catch (LiquidacionException e) {

			}
		}

		persona = personaService.findByLegajoId(legajoId);
		// Verifica si el estado es 9/N no liquida
		if (persona.getEstado() == 9 && persona.getLiquida().equals("N")) {
			try {
				Liquidacion liquidacion = liquidacionService.findByPeriodoAnterior(legajoId, anho, mes);
				if (liquidacion.getEstado() == 1 && liquidacion.getLiquida().equals("S")) {
					log.debug("SIN Liquidacion {}/{}/{}", legajoId, anho, mes);
					return;
				}
			} catch (LiquidacionException e) {

			}
		}
		// Verifica si el estado anterior era 9/S para pasarlo a 9/N
		if (!(persona.getEstado() == 1 && persona.getLiquida().equals("S"))) {
			try {
				Liquidacion liquidacion = liquidacionService.findByPeriodoAnterior(legajoId, anho, mes);
				if (liquidacion.getEstado() == 9 && liquidacion.getLiquida().equals("S")) {
					persona.setLiquida("N");
					persona = personaService.update(persona, legajoId);
					return;
				}
			} catch (LiquidacionException e) {

			}
		}

		// Comienza la liquidación
		makeContext(legajoId, anho, mes);
		antiguedadService.calculate(legajoId, anho, mes);
		liquidacionAdicionalService.deleteAllByLegajo(legajoId, anho, mes);
		itemService.deleteAllByLegajo(legajoId, anho, mes);
		liquidacionService.deleteByLegajo(legajoId, anho, mes);

		List<CodigoGrupo> codigoGrupos = codigoGrupoService.findAll();

		// Verifica si tiene cargoLiquidacions para liquidar o si tiene aguinaldo o
		// vacaciones
		if (!(novedades.containsKey(3) || novedades.containsKey(29) || novedades.containsKey(57))) {
			// Verifica si se puede liquidar, para esto revisa si tiene cargos o cargos con
			// clases
			boolean liquidable = false;
			if (cargoLiquidacionService.findAllActivosByLegajo(legajoId, anho, mes).size() > 0) {
				liquidable = true;
			}
			if (cargoClaseDetalleService.findAllByLegajo(legajoId, anho, mes).size() > 0) {
				liquidable = true;
			}
			if (!liquidable) {
				return;
			}
		}
		BigDecimal coeficienteLiquidacion = BigDecimal.ONE;
		if (mes == 6 || mes == 12) {
			coeficienteLiquidacion = new BigDecimal(1.5);
		}

		// Calcula basico y antiguedad sobre cargoLiquidacions
		calculaBasicoAndAntiguedad(legajoId, anho, mes);

		// Incentivo Posgrado
		BigDecimal incentivoPosgrado = BigDecimal.ZERO;
		switch (persona.getPosgrado()) {
		case 1:
			incentivoPosgrado = control.getDoctorado();
			break;
		case 2:
			incentivoPosgrado = control.getMaestria();
			break;
		case 3:
			incentivoPosgrado = control.getEspecializacion();
			break;
		}
		if (incentivoPosgrado.compareTo(BigDecimal.ZERO) > 0) {
			Integer codigoId = 9;
			if (!items.containsKey(codigoId)) {
				items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
						BigDecimal.ZERO, null, null));
			}
			Item item = items.get(codigoId);
			item.setImporte(item.getImporte().add(incentivoPosgrado));
		}

		// Remunerativos
		for (CodigoGrupo codigoGrupo : codigoGrupos.stream().filter(c -> c.getRemunerativo() == 1)
				.collect(Collectors.toList())) {
			Integer codigoId = codigoGrupo.getCodigoId();
			if (novedades.containsKey(codigoId)) {
				BigDecimal value = novedades.get(codigoId).getImporte();
				if (!items.containsKey(codigoId)) {
					items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
							BigDecimal.ZERO, null, null));
				}
				Item item = items.get(codigoId);
				item.setImporte(item.getImporte().add(value));
			}
		}

		// Desarraigo
		BigDecimal totalDesarraigo = BigDecimal.ZERO;
		for (CursoDesarraigo desarraigo : cursoDesarraigoService.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes)) {
			totalDesarraigo = totalDesarraigo.add(desarraigo.getImporte());
		}
		if (totalDesarraigo.compareTo(BigDecimal.ZERO) != 0) {
			Integer codigoId = 21;
			if (!items.containsKey(codigoId)) {
				items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
						BigDecimal.ZERO, null, null));
			}
			Item item = items.get(codigoId);
			item.setImporte(item.getImporte().add(totalDesarraigo).setScale(2, RoundingMode.HALF_UP));
		}

		// Calcula basico y antiguedad sobre cargos con clase
		for (CargoClaseDetalle detalle : cargoClaseDetalleService.findAllByLegajo(legajoId, anho, mes)) {
			BigDecimal basico = detalle.getValorHora().multiply(new BigDecimal(detalle.getHoras())).setScale(2,
					RoundingMode.HALF_UP);
			Integer codigoId = 1;
			if (!items.containsKey(codigoId)) {
				items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
						BigDecimal.ZERO, null, null));
			}
			Item item = items.get(codigoId);
			item.setImporte(item.getImporte().add(basico));

			BigDecimal antiguedad = basico.multiply(indices.get(0)).setScale(2, RoundingMode.HALF_UP);
			codigoId = 2;
			if (!items.containsKey(codigoId)) {
				items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
						BigDecimal.ZERO, null, null));
			}
			item = items.get(codigoId);
			item.setImporte(item.getImporte().add(antiguedad));
		}

		// Aguinaldo
		BigDecimal aguinaldo = BigDecimal.ZERO;
		if (novedades.containsKey(3)) {
			aguinaldo = novedades.get(3).getImporte();
		}

		// Carga de Novedades No Remunerativas
		for (CodigoGrupo codigoGrupo : codigoGrupos.stream().filter(c -> c.getNoRemunerativo() == 1)
				.collect(Collectors.toList())) {
			Integer codigoId = codigoGrupo.getCodigoId();
			if (novedades.containsKey(codigoId)) {
				BigDecimal value = novedades.get(codigoId).getImporte();
				if (!items.containsKey(codigoId)) {
					items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
							BigDecimal.ZERO, null, null));
				}
				Item item = items.get(codigoId);
				item.setImporte(item.getImporte().add(value));
			}
		}

		// Remunerativos
		BigDecimal totalRemunerativo = BigDecimal.ZERO;
		Integer codigoId = 96;
		if (!items.containsKey(codigoId)) {
			items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
					BigDecimal.ZERO, null, null));
		}
		BigDecimal value = BigDecimal.ZERO;
		for (CodigoGrupo codigoGrupo : codigoGrupos.stream().filter(c -> c.getRemunerativo() == 1)
				.collect(Collectors.toList())) {
			if (codigoGrupo.getCodigoId() != 18) {
				if (items.containsKey(codigoGrupo.getCodigoId())) {
					Item item = items.get(codigoGrupo.getCodigoId());
					log.debug("Item Remunerativo -> {}", item);
					value = value.add(item.getImporte()).setScale(2, RoundingMode.HALF_UP);
				}
			}
		}
		Item item = items.get(codigoId);
		item.setImporte(value);
		log.debug("Item Total Remunerativo -> {}", item);
		totalRemunerativo = value.setScale(2, RoundingMode.HALF_UP);

		// No remunerativos
		codigoId = 97;
		if (!items.containsKey(codigoId)) {
			items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
					BigDecimal.ZERO, null, null));
		}
		value = BigDecimal.ZERO;
		for (CodigoGrupo codigoGrupo : codigoGrupos.stream().filter(c -> c.getNoRemunerativo() == 1)
				.collect(Collectors.toList())) {
			if (items.containsKey(codigoGrupo.getCodigoId())) {
				value = value.add(items.get(codigoGrupo.getCodigoId()).getImporte()).setScale(2, RoundingMode.HALF_UP);
			}
		}
		item = items.get(codigoId);
		item.setImporte(item.getImporte().add(value).setScale(2, RoundingMode.HALF_UP));

		// Inasistencias
		log.debug("Total Remunerativo -> {}", totalRemunerativo);
		aguinaldo = BigDecimal.ZERO;
		codigoId = 3;
		if (items.containsKey(codigoId)) {
			item = items.get(codigoId);
			aguinaldo = item.getImporte();
		}
		log.debug("Aguinaldo -> {}", aguinaldo);
		BigDecimal valorMes = totalRemunerativo.subtract(aguinaldo).setScale(2, RoundingMode.HALF_UP);
		BigDecimal valorDia = valorMes.divide(new BigDecimal(30), 5, RoundingMode.HALF_UP);
		log.debug("Valor Día -> {}", valorDia);
		Integer diasInasistencia = 0;
		if (novedades.containsKey(18)) {
			diasInasistencia = novedades.get(18).getImporte().intValue();
			BigDecimal inasistencias = new BigDecimal(-1).multiply(new BigDecimal(diasInasistencia).multiply(valorDia))
					.setScale(2, RoundingMode.HALF_UP);
			log.debug("Descuento Inasistencias -> {}", inasistencias);
			codigoId = 18;
			if (!items.containsKey(codigoId)) {
				items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
						BigDecimal.ZERO, null, null));
			}
			item = items.get(codigoId);
			item.setImporte(inasistencias.setScale(2, RoundingMode.HALF_UP));
		}

		// Licencia maternidad
		if (novedades.containsKey(30)) {
			diasInasistencia = novedades.get(30).getImporte().intValue();
			log.debug("Días Inasistencia -> {}", diasInasistencia);
			if (persona.getEstado() == 4 && diasInasistencia > 0) {
				BigDecimal licenciaMaternidad = new BigDecimal(-1)
						.multiply(new BigDecimal(diasInasistencia).multiply(valorDia))
						.setScale(2, RoundingMode.HALF_UP);
				codigoId = 30;
				if (!items.containsKey(codigoId)) {
					items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
							BigDecimal.ZERO, null, null));
				}
				item = items.get(codigoId);
				item.setImporte(item.getImporte().add(licenciaMaternidad).setScale(2, RoundingMode.HALF_UP));
			}
		}

		// Remunerativos
		codigoId = 96;
		if (!items.containsKey(codigoId)) {
			items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
					BigDecimal.ZERO, null, null));
		}
		value = BigDecimal.ZERO;
		for (CodigoGrupo codigoGrupo : codigoGrupos.stream().filter(c -> c.getRemunerativo() == 1)
				.collect(Collectors.toList())) {
			if (items.containsKey(codigoGrupo.getCodigoId())) {
				value = value.add(items.get(codigoGrupo.getCodigoId()).getImporte()).setScale(2, RoundingMode.HALF_UP);
			}
		}
		item = items.get(codigoId);
		item.setImporte(value.setScale(2, RoundingMode.HALF_UP));

		// No remunerativos
		codigoId = 97;
		if (!items.containsKey(codigoId)) {
			items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
					BigDecimal.ZERO, null, null));
		}
		value = BigDecimal.ZERO;
		for (CodigoGrupo codigoGrupo : codigoGrupos.stream().filter(c -> c.getNoRemunerativo() == 1)
				.collect(Collectors.toList())) {
			if (items.containsKey(codigoGrupo.getCodigoId())) {
				value = value.add(items.get(codigoGrupo.getCodigoId()).getImporte()).setScale(2, RoundingMode.HALF_UP);
			}
		}
		item = items.get(codigoId);
		item.setImporte(value.setScale(2, RoundingMode.HALF_UP));

		// Jubilacion
		BigDecimal conAportes = items.get(96).getImporte();
		BigDecimal coeficiente = control.getJubilaem().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
		if (conAportes.compareTo(coeficienteLiquidacion.multiply(control.getMaximo1sijp())) <= 0) {
			value = conAportes.multiply(coeficiente).setScale(2, RoundingMode.HALF_UP);
		} else {
			value = coeficienteLiquidacion.multiply(coeficiente).multiply(control.getMaximo1sijp()).setScale(2,
					RoundingMode.HALF_UP);
		}

		if (value.compareTo(BigDecimal.ZERO) != 0) {
			codigoId = 61;
			if (!items.containsKey(codigoId)) {
				items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
						BigDecimal.ZERO, null, null));
			}
			item = items.get(codigoId);
			item.setImporte(item.getImporte().add(value));
		}

		// Jubilacion Secundario
		boolean onlyETEC = false;
		boolean hasETEC = false;
		boolean hasBasico = false;
		codigoId = 1;
		if (items.containsKey(codigoId)) {
			if (items.get(codigoId).getImporte().compareTo(BigDecimal.ZERO) != 0) {
				hasBasico = true;
			}
		}
		codigoId = 29;
		if (items.containsKey(codigoId)) {
			if (items.get(codigoId).getImporte().compareTo(BigDecimal.ZERO) != 0) {
				hasETEC = true;
			}
		}
		if (hasETEC && !hasBasico) {
			onlyETEC = true;
		}

		if (onlyETEC) {
			conAportes = items.get(96).getImporte();
			coeficiente = new BigDecimal(0.02);
			value = conAportes.multiply(coeficiente).setScale(2, RoundingMode.HALF_UP);

			if (value.compareTo(BigDecimal.ZERO) != 0) {
				codigoId = 70;
				if (!items.containsKey(codigoId)) {
					items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
							BigDecimal.ZERO, null, null));
				}
				item = items.get(codigoId);
				item.setImporte(item.getImporte().add(value));
			}
		}

		// INSSJP
		coeficiente = control.getInssjpem().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
		if (conAportes.compareTo(coeficienteLiquidacion.multiply(control.getMaximo5sijp())) <= 0) {
			value = conAportes.multiply(coeficiente).setScale(2, RoundingMode.HALF_UP);
		} else {
			value = coeficienteLiquidacion.multiply(coeficiente).multiply(control.getMaximo5sijp()).setScale(2,
					RoundingMode.HALF_UP);
		}
		if (persona.getEstadoAfip() == 2) {
			value = BigDecimal.ZERO;
		}

		if (value.compareTo(BigDecimal.ZERO) != 0) {
			codigoId = 62;
			if (!items.containsKey(codigoId)) {
				items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
						BigDecimal.ZERO, null, null));
			}
			item = items.get(codigoId);
			item.setImporte(item.getImporte().add(value));
		}

		// Obra Social
		coeficiente = control.getOsociaem().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
		if (conAportes.compareTo(coeficienteLiquidacion.multiply(control.getMinimoAporte())) < 0) {
			value = coeficiente.multiply(coeficienteLiquidacion.multiply(control.getMinimoAporte())).setScale(2,
					RoundingMode.HALF_UP);
		}
		if (conAportes.compareTo(coeficienteLiquidacion.multiply(control.getMinimoAporte())) >= 0
				&& conAportes.compareTo(coeficienteLiquidacion.multiply(control.getMaximoAporte())) <= 0) {
			value = coeficiente.multiply(conAportes).setScale(2, RoundingMode.HALF_UP);
		}
		if (conAportes.compareTo(coeficienteLiquidacion.multiply(control.getMaximoAporte())) > 0) {
			value = coeficiente.multiply(coeficienteLiquidacion.multiply(control.getMaximoAporte())).setScale(2,
					RoundingMode.HALF_UP);
		}
		if (persona.getEstadoAfip() == 2 || conAportes.compareTo(BigDecimal.ZERO) == 0) {
			value = BigDecimal.ZERO;
		}

		if (value.compareTo(BigDecimal.ZERO) != 0) {
			codigoId = 63;
			if (!items.containsKey(codigoId)) {
				items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
						BigDecimal.ZERO, null, null));
			}
			item = items.get(codigoId);
			item.setImporte(item.getImporte().add(value));
		}

		// Deducciones
		for (CodigoGrupo codigoGrupo : codigoGrupos.stream().filter(c -> c.getDeduccion() == 1)
				.collect(Collectors.toList())) {
			codigoId = codigoGrupo.getCodigoId();
			if (novedades.containsKey(codigoId)) {
				value = novedades.get(codigoId).getImporte();
				if (value.compareTo(BigDecimal.ZERO) != 0) {
					if (!items.containsKey(codigoId)) {
						items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId,
								codigos.get(codigoId).getNombre(), BigDecimal.ZERO, null, null));
					}
					item = items.get(codigoId);
					item.setImporte(item.getImporte().add(value));
				}
			}
		}

		// Total deducciones
		codigoId = 98;
		if (!items.containsKey(codigoId)) {
			items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
					BigDecimal.ZERO, null, null));
		}
		value = BigDecimal.ZERO;
		for (CodigoGrupo codigoGrupo : codigoGrupos.stream().filter(c -> c.getDeduccion() == 1)
				.collect(Collectors.toList())) {
			if (items.containsKey(codigoGrupo.getCodigoId())) {
				value = value.add(items.get(codigoGrupo.getCodigoId()).getImporte()).setScale(2, RoundingMode.HALF_UP);
			}
		}
		item = items.get(codigoId);
		item.setImporte(item.getImporte().add(value).setScale(2, RoundingMode.HALF_UP));

		// Ajuste
		BigDecimal neto = items.get(96).getImporte().add(items.get(97).getImporte())
				.subtract(items.get(98).getImporte());
		BigDecimal centavos = neto.subtract(neto.setScale(0, RoundingMode.HALF_UP));
		if (centavos.compareTo(BigDecimal.ZERO) > 0) {
			value = centavos.abs();
			codigoId = 75;
			if (!items.containsKey(codigoId)) {
				items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
						BigDecimal.ZERO, null, null));
			}
			item = items.get(codigoId);
			item.setImporte(item.getImporte().add(value));

			codigoId = 98;
			if (!items.containsKey(codigoId)) {
				items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
						BigDecimal.ZERO, null, null));
			}
			item = items.get(codigoId);
			item.setImporte(item.getImporte().add(value));
		}
		if (centavos.compareTo(BigDecimal.ZERO) < 0) {
			value = centavos.abs();
			codigoId = 45;
			if (!items.containsKey(codigoId)) {
				items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
						BigDecimal.ZERO, null, null));
			}
			item = items.get(codigoId);
			item.setImporte(item.getImporte().add(value));

			codigoId = 97;
			if (!items.containsKey(codigoId)) {
				items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
						BigDecimal.ZERO, null, null));
			}
			item = items.get(codigoId);
			item.setImporte(item.getImporte().add(value));
		}

		// Neto
		value = items.get(96).getImporte().add(items.get(97).getImporte()).subtract(items.get(98).getImporte());
		codigoId = 99;
		if (!items.containsKey(codigoId)) {
			items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
					BigDecimal.ZERO, null, null));
		}
		item = items.get(codigoId);
		item.setImporte(item.getImporte().add(value));

		// Eliminar después de detectar la duplicación
		itemService.deleteAllByLegajo(legajoId, anho, mes);
		//
		itemService.saveAll(new ArrayList<Item>(items.values()));

		liquidacionService.add(new Liquidacion(null, legajoId, anho, mes, Tool.dateAbsoluteArgentina(), null,
				persona.getDependenciaId(), persona.getSalida(), items.get(96).getImporte(), items.get(97).getImporte(),
				items.get(98).getImporte(), items.get(99).getImporte(), (byte) 0, persona.getEstado(),
				persona.getLiquida(), null, null));

		try {
			actividadService.findByUnique(legajoId, anho, mes);
		} catch (ActividadNotFoundException e) {
			actividadService
					.add(new Actividad(null, legajoId, anho, mes, (byte) 1, (byte) 1, (byte) 1, 10, null, null));
		}

		try {
			letraService.findByUnique(legajoId, anho, mes);
		} catch (LetraNotFoundException e) {
			letraService.add(new Letra(null, legajoId, anho, mes, BigDecimal.ZERO, ""));
		}

		LegajoControl legajoControl = legajoControlService.findByUnique(legajoId, anho, mes);
		legajoControl.setLiquidado((byte) 1);
		legajoControlService.update(legajoControl, legajoControl.getLegajoControlId());

	}

	@Transactional
	public void deleteLiquidacionByLegajoId(Long legajoId, Integer anho, Integer mes, Boolean force) {
		// Verifica si ya fue acreditado el período para ver si se puede liquidar
		if (!force) {
			try {
				Liquidacion liquidacion = liquidacionService.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
				if (liquidacion.getBloqueado() == (byte) 1) {
					return;
				}
			} catch (LiquidacionException e) {

			}
		}

		liquidacionAdicionalService.deleteAllByLegajo(legajoId, anho, mes);
		letraService.deleteByUnique(legajoId, anho, mes);
		itemService.deleteAllByLegajo(legajoId, anho, mes);
		liquidacionService.deleteByLegajo(legajoId, anho, mes);
		// Poner liquidado y bloqueado en legajoControl en 0
		LegajoControl legajoControl = legajoControlService.findByUnique(legajoId, anho, mes);
		legajoControl.setLiquidado((byte) 0);
		legajoControl = legajoControlService.update(legajoControl, legajoControl.getLegajoControlId());
	}

	@Transactional
	private void calculaBasicoAndAntiguedad(Long legajoId, Integer anho, Integer mes) {
		// Calcula sobre Cursos (Adicional)
		// cantidad de horas por dependencia
		Map<Integer, Integer> horasDependencia = new HashMap<Integer, Integer>();
		for (CursoCargo cursoCargo : cursoCargoService.findAllByLegajoWithAdicional(legajoId, anho, mes)) {
			Dependencia dependencia = dependenciaService.findFirstByFacultadIdAndGeograficaId(
					cursoCargo.getCurso().getFacultadId(), cursoCargo.getCurso().getGeograficaId());
			if (!horasDependencia.containsKey(dependencia.getDependenciaId())) {
				horasDependencia.put(dependencia.getDependenciaId(), 0);
			}
			horasDependencia.replace(dependencia.getDependenciaId(),
					horasDependencia.get(dependencia.getDependenciaId()) + cursoCargo.getHorasSemanales().intValue());
		}
		log.debug("Horas Dependencia -> {}", horasDependencia);
		// sumatoria de cursoFusion para cada dependencia
		Map<Integer, BigDecimal> totalDependencia = new HashMap<Integer, BigDecimal>();
		for (CursoFusion cursoFusion : cursoFusionService.findAllByLegajoId(legajoId, anho, mes)) {
			Dependencia dependencia = dependenciaService
					.findFirstByFacultadIdAndGeograficaId(cursoFusion.getFacultadId(), cursoFusion.getGeograficaId());
			if (!totalDependencia.containsKey(dependencia.getDependenciaId())) {
				totalDependencia.put(dependencia.getDependenciaId(), BigDecimal.ZERO);
			}
			try {
				CategoriaPeriodo categoriaPeriodo = categoriaPeriodoService.findByUnique(cursoFusion.getCategoriaId(),
						anho, mes);
				totalDependencia.put(dependencia.getDependenciaId(),
						totalDependencia.get(dependencia.getDependenciaId()).add(categoriaPeriodo.getBasico())
								.setScale(2, RoundingMode.HALF_UP));
			} catch (CategoriaPeriodoNotFoundException e) {
				Categoria categoria = categoriaService.findByCategoriaId(cursoFusion.getCategoriaId());
				totalDependencia.put(dependencia.getDependenciaId(),
						totalDependencia.get(dependencia.getDependenciaId()).add(categoria.getBasico()).setScale(2,
								RoundingMode.HALF_UP));
			}
		}
		log.debug("Total Dependencia -> {}", totalDependencia);
		// Calcula basico y antiguedad para cada dependencia
		for (Integer dependenciaId : totalDependencia.keySet()) {
			try {
				Dependencia dependencia = dependenciaService.findByDependenciaId(dependenciaId);
				AdicionalCursoTabla adicionalCursoTabla = adicionalCursoTablaService
						.findByFacultadIdAndPeriodo(dependencia.getFacultadId(), anho, mes);
				Integer horas = horasDependencia.get(dependenciaId);
				BigDecimal totalCategoria = totalDependencia.get(dependenciaId);
				BigDecimal porcentaje = BigDecimal.ZERO;
				for (AdicionalCursoRango adicionalCursoRango : adicionalCursoTabla.getAdicionalCursoRangos()) {
					if (horas >= adicionalCursoRango.getHorasDesde() && horas <= adicionalCursoRango.getHorasHasta()) {
						porcentaje = adicionalCursoRango.getPorcentaje();
					}
				}
				log.debug("Horas -> {} - Total -> {} - Porcentaje -> {}", horas, totalCategoria, porcentaje);
				BigDecimal adicional = totalCategoria.multiply(porcentaje).divide(new BigDecimal(100),
						RoundingMode.HALF_UP);
				if (adicional.compareTo(BigDecimal.ZERO) > 0) {
					LiquidacionAdicional liquidacionAdicional = new LiquidacionAdicional(null, legajoId, anho, mes,
							dependenciaId, adicional, null, null);
					liquidacionAdicional = liquidacionAdicionalService.add(liquidacionAdicional);
					// Basico
					Integer codigoId = 1;
					Item item = null;
					if (!items.containsKey(codigoId)) {
						items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId,
								codigos.get(codigoId).getNombre(), BigDecimal.ZERO, null, null));
					}
					item = items.get(codigoId);
					item.setImporte(item.getImporte().add(adicional).setScale(2, RoundingMode.HALF_UP));

					// Antigüedad
					BigDecimal antiguedad = BigDecimal.ZERO;
					antiguedad = adicional.multiply(indices.get(0)).setScale(2, RoundingMode.HALF_UP);
					codigoId = 2;
					if (antiguedad.compareTo(BigDecimal.ZERO) != 0) {
						if (!items.containsKey(codigoId)) {
							items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId,
									codigos.get(codigoId).getNombre(), BigDecimal.ZERO, null, null));
						}
						item = items.get(codigoId);
						item.setImporte(item.getImporte().add(antiguedad).setScale(2, RoundingMode.HALF_UP));
					}

				}
			} catch (AdicionalCursoTablaNotFoundException e) {
				log.debug("Sin Tabla Adicional");
			}
		}

		// Calcula sobre Cargos
		for (CargoLiquidacion cargoLiquidacion : cargoLiquidacionService.findAllByLegajo(legajoId, anho, mes)) {
			BigDecimal valorMes = BigDecimal.ZERO;

			// Basico
			BigDecimal basico = cargoLiquidacion.getCategoriaBasico()
					.multiply(new BigDecimal(cargoLiquidacion.getJornada())).setScale(2, RoundingMode.HALF_UP);
			BigDecimal horas = BigDecimal.ONE;
			if (cargoLiquidacion.getHorasJornada().compareTo(BigDecimal.ZERO) > 0) {
				horas = cargoLiquidacion.getHorasJornada();
			}
			basico = basico.multiply(horas).setScale(2, RoundingMode.HALF_UP);
			Categoria categoria = cargoLiquidacion.getCategoria();
			if (categoria.getBasico().compareTo(basico) > 0) {
				basico = categoria.getBasico();
			}
			valorMes = valorMes.add(basico).setScale(2, RoundingMode.HALF_UP);
			Integer codigoId = 1;
			Item item = null;
			if (basico.compareTo(BigDecimal.ZERO) != 0) {
				if (!items.containsKey(codigoId)) {
					items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
							BigDecimal.ZERO, null, null));
				}
				item = items.get(codigoId);
				item.setImporte(item.getImporte().add(basico).setScale(2, RoundingMode.HALF_UP));
			}

			// Antigüedad
			BigDecimal antiguedad = BigDecimal.ZERO;
			if (cargoLiquidacion.getCategoria().getNoDocente() == 1) {
				antiguedad = basico.multiply(indices.get(1).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
			}
			if (cargoLiquidacion.getCategoria().getDocente() == 1) {
				antiguedad = basico.multiply(indices.get(0)).setScale(2, RoundingMode.HALF_UP);
			}
			valorMes = valorMes.add(antiguedad).setScale(2, RoundingMode.HALF_UP);
			codigoId = 2;
			if (antiguedad.compareTo(BigDecimal.ZERO) != 0) {
				if (!items.containsKey(codigoId)) {
					items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
							BigDecimal.ZERO, null, null));
				}
				item = items.get(codigoId);
				item.setImporte(item.getImporte().add(antiguedad).setScale(2, RoundingMode.HALF_UP));
			}

			// Calcula estado docente
			BigDecimal estadoDocente = BigDecimal.ZERO;
			if ((cargoLiquidacion.getCategoriaId() > 100 && cargoLiquidacion.getCategoriaId() < 201)
					|| (cargoLiquidacion.getCategoriaId() > 205 && cargoLiquidacion.getCategoriaId() < 210)
					|| (cargoLiquidacion.getCategoriaId() > 214 && cargoLiquidacion.getCategoriaId() < 218)
					|| (cargoLiquidacion.getCategoriaId() > 500 && cargoLiquidacion.getCategoriaId() < 701)
					|| (cargoLiquidacion.getCategoriaId() > 900 && cargoLiquidacion.getCategoriaId() < 980))
				estadoDocente = control.getEstadoDocenteTitular();

			if ((cargoLiquidacion.getCategoriaId() > 200 && cargoLiquidacion.getCategoriaId() < 206)
					|| (cargoLiquidacion.getCategoriaId() > 209 && cargoLiquidacion.getCategoriaId() < 215)
					|| (cargoLiquidacion.getCategoriaId() > 216 && cargoLiquidacion.getCategoriaId() < 301))
				estadoDocente = control.getEstadoDocenteAdjunto();

			if ((cargoLiquidacion.getCategoriaId() > 300 && cargoLiquidacion.getCategoriaId() < 501))
				estadoDocente = control.getEstadoDocenteAuxiliar();

			if (basico.compareTo(BigDecimal.ZERO) == 0)
				estadoDocente = basico;

			valorMes = valorMes.add(estadoDocente).setScale(2, RoundingMode.HALF_UP);

			if (estadoDocente.compareTo(BigDecimal.ZERO) != 0) {
				codigoId = 4;
				if (!items.containsKey(codigoId)) {
					items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
							BigDecimal.ZERO, null, null));
				}
				item = items.get(codigoId);
				item.setImporte(item.getImporte().add(estadoDocente).setScale(2, RoundingMode.HALF_UP));
			}

			// Presentismo
			BigDecimal presentismoSi = BigDecimal.ZERO;
			BigDecimal presentismoNo = BigDecimal.ZERO;

			if (cargoLiquidacion.getCategoria().getNoDocente() == 1) {
				presentismoSi = basico.multiply(new BigDecimal(cargoLiquidacion.getPresentismo())
						.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
				codigoId = 16;
				if (!items.containsKey(codigoId)) {
					items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
							BigDecimal.ZERO, null, null));
				}
				item = items.get(codigoId);
				item.setImporte(item.getImporte().add(presentismoSi).setScale(2, RoundingMode.HALF_UP));
			}
			valorMes = valorMes.add(presentismoSi);

			codigoId = 16;
			if (novedades.containsKey(codigoId)) {
				if (novedades.get(codigoId).getValue().equals("N")) {
					presentismoNo = basico.multiply(new BigDecimal(cargoLiquidacion.getPresentismo())
							.divide(new BigDecimal(-100), 2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
					codigoId = 17;
					if (!items.containsKey(codigoId)) {
						items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId,
								codigos.get(codigoId).getNombre(), BigDecimal.ZERO, null, null));
					}
					item = items.get(codigoId);
					item.setImporte(item.getImporte().add(presentismoNo).setScale(2, RoundingMode.HALF_UP));
				}
			}

			// Horas extras
			codigoId = 19;
			if (novedades.containsKey(codigoId)) {
				Integer totalHoras = cargoLiquidacion.getJornada() == 1 ? 100 : 180;
				BigDecimal unaHoraTrabajada = valorMes.divide(new BigDecimal(totalHoras), 2, RoundingMode.HALF_UP);
				BigDecimal horasExtras = unaHoraTrabajada.multiply(novedades.get(19).getImporte()).setScale(2,
						RoundingMode.HALF_UP);
				codigoId = 19;
				if (!items.containsKey(codigoId)) {
					items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(),
							BigDecimal.ZERO, null, null));
				}
				item = items.get(codigoId);
				item.setImporte(item.getImporte().add(horasExtras).setScale(2, RoundingMode.HALF_UP));
			}
		}
	}

	@Transactional
	public void generateCargosDocentes(Long legajoId, Integer anho, Integer mes) {
		cargoLiquidacionService.deleteAllCargosDocentes(legajoId, anho, mes);
		Map<String, Dependencia> dependencias = dependenciaService.findAll().stream().collect(Collectors
				.toMap(Dependencia::getSedeKey, Function.identity(), (dependencia, replacement) -> dependencia));
		List<CargoLiquidacion> cargoLiquidacions = new ArrayList<CargoLiquidacion>();
		for (Cargo cargo : cargoService.findAllDocenteByPeriodo(legajoId, anho, mes)) {
			log.debug("Cargo -> {}", cargo);
			Categoria categoria = cargo.getCategoria();
			if (categoria.getCategoriaId() != 980) {
				CargoLiquidacion cargoLiquidacion = null;
				cargoLiquidacions.add(
						cargoLiquidacion = new CargoLiquidacion(null, legajoId, anho, mes, cargo.getDependenciaId(),
								Periodo.firstDay(anho, mes), Periodo.lastDay(anho, mes), categoria.getCategoriaId(),
								categoria.getNombre(), categoria.getBasico(), cargo.getHorasJornada(),
								cargo.getJornada(), cargo.getPresentismo(), "A", null, null, categoria));
				log.debug("CargoLiquidacion -> {}", cargoLiquidacion);
			}
		}
		for (CursoFusion cursoFusion : cursoFusionService.findAllByLegajoId(legajoId, anho, mes)) {
			log.debug("Curso Fusion -> {}", cursoFusion);
			Dependencia dependencia = dependencias
					.get(cursoFusion.getFacultadId() + "." + cursoFusion.getGeograficaId());
			log.debug("Dependencia -> {}", dependencia);
			Categoria categoria = cursoFusion.getCategoria();
			log.debug("Categoria -> {}", categoria);
			cargoLiquidacions.add(new CargoLiquidacion(null, legajoId, anho, mes, dependencia.getDependenciaId(),
					Periodo.firstDay(anho, mes), Periodo.lastDay(anho, mes), categoria.getCategoriaId(),
					categoria.getNombre(), categoria.getBasico(), BigDecimal.ZERO, 1, 0, "A", null, dependencia,
					categoria));
		}
		Categoria categoria = categoriaService.findByCategoriaId(980);
		for (Novedad novedad : novedadService.findAllByLegajoAndCodigo(legajoId, anho, mes, 980)) {
			cargoLiquidacions.add(new CargoLiquidacion(null, legajoId, anho, mes, novedad.getDependenciaId(),
					Periodo.firstDay(anho, mes), Periodo.lastDay(anho, mes), categoria.getCategoriaId(),
					categoria.getNombre(), novedad.getImporte(), BigDecimal.ZERO, 1, 0, "A", null,
					novedad.getDependencia(), categoria));
		}
		cargoLiquidacions = cargoLiquidacionService.saveAll(cargoLiquidacions, mes, false);
	}

	@Transactional
	public void generateCargosNoDocentes(Long legajoId, Integer anho, Integer mes) {
		List<CargoLiquidacion> cargoLiquidacions = new ArrayList<CargoLiquidacion>();
		cargoLiquidacionService.deleteAllCargosNoDocentes(legajoId, anho, mes);
		for (Cargo cargo : cargoService.findAllNoDocenteByPeriodo(legajoId, anho, mes)) {
			log.debug("Cargo -> {}", cargo);
			Categoria categoria = cargo.getCategoria();
			if (categoria.getCategoriaId() != 980) {
				CargoLiquidacion cargoLiquidacion = null;
				cargoLiquidacions.add(
						cargoLiquidacion = new CargoLiquidacion(null, legajoId, anho, mes, cargo.getDependenciaId(),
								Periodo.firstDay(anho, mes), Periodo.lastDay(anho, mes), categoria.getCategoriaId(),
								categoria.getNombre(), categoria.getBasico(), cargo.getHorasJornada(),
								cargo.getJornada(), cargo.getPresentismo(), "A", null, null, categoria));
				log.debug("CargoLiquidacion -> {}", cargoLiquidacion);
			}
		}
		cargoLiquidacions = cargoLiquidacionService.saveAll(cargoLiquidacions, mes, false);
	}

	@Transactional
	public void deleteNovedadDuplicada(Integer anho, Integer mes) {
		for (NovedadDuplicada novedadDuplicada : novedadDuplicadaService.findAllByPeriodo(anho, mes)) {
			Boolean first = true;
			for (Novedad novedad : novedadService.findAllByLegajoAndCodigo(novedadDuplicada.getLegajoId(), anho, mes,
					novedadDuplicada.getCodigoId())) {
				if (first) {
					first = false;
				} else {
					novedadService.deleteByNovedadId(novedad.getNovedadId());
				}
			}
		}
	}

	@Transactional
	public void desmarcaPeriodo(Integer anho, Integer mes) {
		List<LegajoControl> legajos = legajoControlService.findAllByPeriodo(anho, mes);
		for (LegajoControl legajoControl : legajos) {
			legajoControl.setLiquidado((byte) 0);
		}
		legajos = legajoControlService.saveAll(legajos);
	}

	public String generateSIJP(Integer anho, Integer mes) {
		return null;
	}

}
