/**
 * 
 */
package ar.edu.um.haberes.rest.service.facade;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.AntiguedadLimiteNotFoundException;
import ar.edu.um.haberes.rest.exception.AntiguedadNotFoundException;
import ar.edu.um.haberes.rest.exception.CursoDesarraigoNotFoundException;
import ar.edu.um.haberes.rest.exception.DesignacionNotFoundException;
import ar.edu.um.haberes.rest.exception.LegajoControlNotFoundException;
import ar.edu.um.haberes.rest.model.Antiguedad;
import ar.edu.um.haberes.rest.model.AntiguedadLimite;
import ar.edu.um.haberes.rest.model.CargoTipo;
import ar.edu.um.haberes.rest.model.Curso;
import ar.edu.um.haberes.rest.model.CursoCargo;
import ar.edu.um.haberes.rest.model.CursoDesarraigo;
import ar.edu.um.haberes.rest.model.CursoFusion;
import ar.edu.um.haberes.rest.model.Designacion;
import ar.edu.um.haberes.rest.model.DesignacionTipo;
import ar.edu.um.haberes.rest.model.Facultad;
import ar.edu.um.haberes.rest.model.Geografica;
import ar.edu.um.haberes.rest.model.LegajoControl;
import ar.edu.um.haberes.rest.model.Persona;
import ar.edu.um.haberes.rest.service.AntiguedadLimiteService;
import ar.edu.um.haberes.rest.service.AntiguedadService;
import ar.edu.um.haberes.rest.service.CursoCargoService;
import ar.edu.um.haberes.rest.service.CursoDesarraigoService;
import ar.edu.um.haberes.rest.service.CursoFusionService;
import ar.edu.um.haberes.rest.service.CursoService;
import ar.edu.um.haberes.rest.service.DesignacionService;
import ar.edu.um.haberes.rest.service.DesignacionTipoService;
import ar.edu.um.haberes.rest.service.GeograficaService;
import ar.edu.um.haberes.rest.service.LegajoControlService;
import ar.edu.um.haberes.rest.service.PersonaService;
import ar.edu.um.haberes.rest.util.Periodo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class DesignacionToolService {

	static final List<Integer> excepcionales = List.of(3, 5);

	static final Integer const_Simple = 1;
	static final Integer const_Intermedia = 2;
	static final Integer const_Especial = 3;
	static final Integer const_Extraordinaria = 4;

	static final Integer const_Nivel_Grado = 1;
	static final Integer const_Nivel_Secundario = 2;
	static final Integer const_Nivel_Tecnicatura = 3;

	@Autowired
	private CursoCargoService cursoCargoService;

	@Autowired
	private DesignacionTipoService designacionTipoService;

	@Autowired
	private CursoService cursoService;

	@Autowired
	private DesignacionService designacionService;

	@Autowired
	private LegajoControlService legajoControlService;

	@Autowired
	private CursoFusionService cursoFusionService;

	@Autowired
	private AntiguedadService antiguedadService;

	@Autowired
	private AntiguedadLimiteService antiguedadLimiteService;

	@Autowired
	private CursoDesarraigoService cursoDesarraigoService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private GeograficaService geograficaService;

	@Transactional
	public void convertirGradoByLegajo(Long legajoId, Integer anho, Integer mes, Boolean aplicaExcepcion) {
		log.debug("Convertir Grado By Legajo");
		List<CursoCargo> cursoCargos = redesignarGradoByLegajo(legajoId, anho, mes, aplicaExcepcion);
		fusionarGradoByLegajo(legajoId, anho, mes, cursoCargos, aplicaExcepcion);
		desarraigoGradoByLegajo(legajoId, anho, mes);
	}

	@Transactional
	public List<CursoCargo> redesignarGradoByLegajo(Long legajoId, Integer anho, Integer mes, Boolean aplicaExcepcion) {
		log.debug("Redesignar Grado By Legajo");
		List<CursoCargo> cursoCargos = cursoCargoService.findAllByLegajoAndNivel(legajoId, anho, mes,
				const_Nivel_Grado);
		log.debug("Start CursoCargos -> {}", cursoCargos);
		for (CursoCargo cursoCargo : cursoCargos) {
			DesignacionTipo designacionTipo = designacionTipoService
					.findByHorasSemanales(cursoCargo.getHorasSemanales());
			Curso curso = cursoCargo.getCurso();

			cursoCargo.setDesignacionTipoId(designacionTipo.getDesignacionTipoId());
			cursoCargo.setCategoriaId(calcularCategoriaId(curso.getFacultadId(), cursoCargo.getDesignacionTipoId(),
					cursoCargo.getCargoTipoId(), curso.getAnual(), (byte) (curso.getSemestre1() | curso.getSemestre2()),
					aplicaExcepcion));
			if (cursoCargo.getCategoriaId() != null) {
				cursoCargo = cursoCargoService.update(cursoCargo, cursoCargo.getCursoCargoId());
			} else {
				Facultad facultad = cursoCargo.getCurso().getFacultad();
				CargoTipo cargoTipo = cursoCargo.getCargoTipo();
				String periodo = "";
				if (curso.getAnual() == 1)
					periodo = "anual";
				if (curso.getSemestre1() == 1 || curso.getSemestre2() == 1)
					periodo = "semestral";
				log.debug("ERROR: " + facultad.getNombre() + " - " + designacionTipo.getNombre() + " - "
						+ cargoTipo.getNombre() + " - " + periodo);
			}
		}
		return cursoCargos;
	}

	@Transactional
	public void fusionarGradoByLegajo(Long legajoId, Integer anho, Integer mes, List<CursoCargo> cursoCargos,
			Boolean aplicaExcepcion) {
		log.debug("Fusionar Grado By Legajo");
		LegajoControl legajoControl = null;
		try {
			legajoControl = legajoControlService.findByUnique(legajoId, anho, mes);
		} catch (LegajoControlNotFoundException e) {
			legajoControl = new LegajoControl();
		}
		if (legajoControl.getFusionado() == 1)
			return;
		log.debug("{}", legajoControl);

		if (cursoCargos == null) {
			cursoCargos = cursoCargoService.findAllByLegajoAndNivel(legajoId, anho, mes, const_Nivel_Grado);
		}
		log.debug("CursoCargos -> {}", cursoCargos);

		// Delete all cursoFusion by legajo_id and month
		cursoFusionService.deleteAllByLegajoIdAndPeriodo(legajoId, anho, mes);

		// Toma las facultades de acuerdo a los cargos
		for (Facultad facultad : cursoCargos.stream().map(cursoCargo -> cursoCargo.getCurso().getFacultad())
				.collect(Collectors.toMap(facultad -> facultad.getFacultadId(), Function.identity(),
						(facultad, replacement) -> facultad))
				.values().stream().collect(Collectors.toList())) {
			log.debug("Fusionar Grado Facultad -> {}", facultad);
			// Toma las sedes de acuerdo a los cargos
			for (Geografica geografica : cursoCargos.stream()
					.filter(cursoCargo -> cursoCargo.getCurso().getFacultadId() == facultad.getFacultadId())
					.map(cursoCargo -> cursoCargo.getCurso().getGeografica())
					.collect(Collectors.toMap(geografica -> geografica.getGeograficaId(), Function.identity(),
							(geografica, replacement) -> geografica))
					.values().stream().collect(Collectors.toList())) {
//				cursoFusionService.deleteAllByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(legajoId, anho, mes,
//						facultad.getFacultadId(), geografica.getGeograficaId());
				log.debug("Fusionar Grado Geografica -> {}", geografica);
				// Toma los cargos de acuerdo a facultad y sede
				for (CargoTipo cargoTipo : cursoCargos.stream()
						.filter(cursoCargo -> cursoCargo.getCurso().getFacultadId() == facultad.getFacultadId()
								&& cursoCargo.getCurso().getGeograficaId() == geografica.getGeograficaId())
						.map(cursoCargo -> cursoCargo.getCargoTipo())
						.collect(Collectors.toMap(cargoTipo -> cargoTipo.getCargoTipoId(), Function.identity(),
								(cargoTipo, replacement) -> cargoTipo))
						.values().stream().collect(Collectors.toList())) {
					log.debug("Fusionar Grado CargoTipo -> {}", cargoTipo);
					fusionarGradoByFacultad(legajoId, anho, mes, facultad.getFacultadId(), geografica.getGeograficaId(),
							cargoTipo.getCargoTipoId(),
							cursoCargos.stream().filter(
									cursoCargo -> cursoCargo.getCurso().getFacultadId() == facultad.getFacultadId()
											&& cursoCargo.getCurso().getGeograficaId() == geografica.getGeograficaId()
											&& cursoCargo.getCargoTipoId() == cargoTipo.getCargoTipoId())
									.collect(Collectors.toList()),
							aplicaExcepcion);
				}
			}
		}
		Long legajoControlId = null;
		try {
			legajoControl = legajoControlService.findByUnique(legajoId, anho, mes);
			legajoControlId = legajoControl.getLegajoControlId();
		} catch (LegajoControlNotFoundException e) {
			legajoControlId = null;
		}
		legajoControl = new LegajoControl(legajoControlId, legajoId, anho, mes, legajoControl.getLiquidado(), (byte) 1,
				(byte) 0, null);
		legajoControl = legajoControlService.save(legajoControl);
		log.debug("Fin Fusion");

	}

	@Transactional
	public void fusionarGradoByFacultad(Long legajoId, Integer anho, Integer mes, Integer facultadId,
			Integer geograficaId, Integer cargoTipoId, List<CursoCargo> cursoCargos, Boolean aplicaExcepcion) {
		log.debug("Fusionar Grado por Facultad CursoCargos -> {}", cursoCargos);
		if (!cursoCargos.isEmpty()) {
			fusionarGradoByFacultadAnual(legajoId, anho, mes, facultadId, geograficaId, cargoTipoId, cursoCargos,
					aplicaExcepcion);

			if (aplicaExcepcion)
				if (excepcionales.contains(facultadId))
					return;

			fusionarGradoByFacultadSemestral(legajoId, anho, mes, facultadId, geograficaId, cargoTipoId, cursoCargos);
		}
	}

	@Transactional
	private void fusionarGradoByFacultadAnual(Long legajoId, Integer anho, Integer mes, Integer facultadId,
			Integer geograficaId, Integer cargoTipoId, List<CursoCargo> cursoCargos, Boolean aplicaExcepcion) {
		log.debug("Fusionar Grado por Facultad Anual");
		Integer totalSimplesAnual = 0;
		for (CursoCargo cursoCargo : cursoCargos.stream().filter(curso -> curso.getCurso().getAnual() == 1)
				.collect(Collectors.toList())) {
			totalSimplesAnual += cursoCargo.getDesignacionTipo().getSimples();
		}
		if (aplicaExcepcion) {
			if (excepcionales.contains(facultadId)) {
				for (CursoCargo cursoCargo : cursoCargos.stream().filter(curso -> curso.getCurso().getSemestre1() == 1)
						.collect(Collectors.toList())) {
					totalSimplesAnual += cursoCargo.getDesignacionTipo().getSimples();
				}
				for (CursoCargo cursoCargo : cursoCargos.stream().filter(curso -> curso.getCurso().getSemestre2() == 1)
						.collect(Collectors.toList())) {
					totalSimplesAnual += cursoCargo.getDesignacionTipo().getSimples();
				}
			}
		}
		if (totalSimplesAnual == 0)
			return;
		List<Integer> designaciones = null;
		switch (totalSimplesAnual) {
		case 1:
			designaciones = List.of(const_Simple);
			break;
		case 2:
			designaciones = List.of(const_Intermedia);
			break;
		case 3:
			designaciones = List.of(const_Especial);
			break;
		case 4:
			designaciones = List.of(const_Especial, const_Simple);
			break;
		case 5:
			designaciones = List.of(const_Especial, const_Intermedia);
			break;
		case 6:
			designaciones = List.of(const_Extraordinaria);
			break;
		case 7:
			designaciones = List.of(const_Extraordinaria, const_Simple);
			break;
		case 8:
			designaciones = List.of(const_Extraordinaria, const_Intermedia);
			break;
		case 9:
			designaciones = List.of(const_Extraordinaria, const_Especial);
			break;
		case 10:
			designaciones = List.of(const_Extraordinaria, const_Especial, const_Simple);
			break;
		case 11:
			designaciones = List.of(const_Extraordinaria, const_Especial, const_Intermedia);
			break;
		case 12:
			designaciones = List.of(const_Extraordinaria, const_Extraordinaria);
			break;
		}

		List<CursoFusion> fusiones = new ArrayList<CursoFusion>();
		for (Integer designacionTipoId : designaciones) {
			fusiones.add(new CursoFusion(null, legajoId, anho, mes, facultadId, geograficaId, cargoTipoId,
					designacionTipoId, (byte) 1, this.calcularCategoriaId(facultadId, designacionTipoId, cargoTipoId,
							(byte) 1, (byte) 0, aplicaExcepcion),
					null, null, null, null, null, null));
		}
		fusiones = cursoFusionService.saveAll(fusiones);
	}

	@Transactional
	private void fusionarGradoByFacultadSemestral(Long legajoId, Integer anho, Integer mes, Integer facultadId,
			Integer geograficaId, Integer cargoTipoId, List<CursoCargo> cursoCargos) {
		log.debug("Fusionar Grado por Facultad Semestral");
		Integer totalSimplesSemestre1 = 0;
		for (CursoCargo cursoCargo : cursoCargos.stream().filter(curso -> curso.getCurso().getSemestre1() == 1)
				.collect(Collectors.toList())) {
			totalSimplesSemestre1 += cursoCargo.getDesignacionTipo().getSimples();
		}
		Integer totalSimplesSemestre2 = 0;
		for (CursoCargo cursoCargo : cursoCargos.stream().filter(curso -> curso.getCurso().getSemestre2() == 1)
				.collect(Collectors.toList())) {
			totalSimplesSemestre2 += cursoCargo.getDesignacionTipo().getSimples();
		}
		Integer totalSimplesSemestral = totalSimplesSemestre1 + totalSimplesSemestre2;
		if (totalSimplesSemestral == 0)
			return;

		List<Integer> designaciones = null;
		switch (totalSimplesSemestral) {
		case 1:
			designaciones = List.of(const_Simple);
			break;
		case 2:
			designaciones = List.of(const_Intermedia);
			break;
		case 3:
			designaciones = List.of(const_Especial);
			break;
		case 4:
			designaciones = List.of(const_Especial, const_Simple);
			break;
		case 5:
			designaciones = List.of(const_Especial, const_Intermedia);
			break;
		case 6:
			designaciones = List.of(const_Extraordinaria);
			break;
		case 7:
			designaciones = List.of(const_Extraordinaria, const_Simple);
			break;
		case 8:
			designaciones = List.of(const_Extraordinaria, const_Intermedia);
			break;
		case 9:
			designaciones = List.of(const_Extraordinaria, const_Especial);
			break;
		case 10:
			designaciones = List.of(const_Extraordinaria, const_Especial, const_Simple);
			break;
		case 11:
			designaciones = List.of(const_Extraordinaria, const_Especial, const_Intermedia);
			break;
		case 12:
			designaciones = List.of(const_Extraordinaria, const_Extraordinaria);
			break;
		case 13:
			designaciones = List.of(const_Extraordinaria, const_Extraordinaria, const_Simple);
			break;
		case 14:
			designaciones = List.of(const_Extraordinaria, const_Extraordinaria, const_Intermedia);
			break;
		case 15:
			designaciones = List.of(const_Extraordinaria, const_Extraordinaria, const_Especial);
			break;
		}

		List<CursoFusion> fusiones = new ArrayList<CursoFusion>();
		for (Integer designacionTipoId : designaciones) {
			fusiones.add(new CursoFusion(null, legajoId, anho, mes, facultadId, geograficaId, cargoTipoId,
					designacionTipoId, (byte) 0,
					this.calcularCategoriaId(facultadId, designacionTipoId, cargoTipoId, (byte) 0, (byte) 1, false),
					null, null, null, null, null, null));
		}
		fusiones = cursoFusionService.saveAll(fusiones);
	}

	@Transactional
	public void desarraigoGradoByLegajo(Long legajoId, Integer anho, Integer mes) {
		// delete CursoDesarraigo that does not exist any more
		List<CursoCargo> cursoCargos = cursoCargoService.findAllByLegajo(legajoId, anho, mes);
		log.debug("CursoCargos -> {}", cursoCargos);
		List<CursoDesarraigo> cursoDesarraigos = cursoDesarraigoService.findAllByLegajoIdAndAnhoAndMes(legajoId, anho,
				mes);
		log.debug("CursoDesarraigos -> {}", cursoDesarraigos);
		for (CursoDesarraigo cursoDesarraigo : cursoDesarraigos) {
			cursoDesarraigo.setVersion(-1);
		}
		log.debug("CursoDesarraigos -> {}", cursoDesarraigos);
		Map<String, CursoDesarraigo> cursoDesarraigoMap = cursoDesarraigos.stream()
				.collect(Collectors.toMap(
						curso -> curso.getLegajoId().toString() + "." + curso.getAnho().toString() + "."
								+ curso.getMes().toString() + "." + curso.getCursoId().toString(),
						cursoDesarraigo -> cursoDesarraigo));
		log.debug("CursoDesarraigoMap -> {}", cursoDesarraigoMap);
		for (CursoCargo cursoCargo : cursoCargos) {
			log.debug("CursoCargo -> {}", cursoCargo);
			String key = cursoCargo.getLegajoId().toString() + "." + cursoCargo.getAnho().toString() + "."
					+ cursoCargo.getMes().toString() + "." + cursoCargo.getCursoId().toString();
			log.debug("key -> {}", key);
			if (cursoDesarraigoMap.containsKey(key)) {
				CursoDesarraigo cursoDesarraigo = cursoDesarraigoMap.get(key);
				log.debug("CursoDesarraigo (antes) -> {}", cursoDesarraigo);
				cursoDesarraigo.setVersion(0);
				log.debug("CursoDesarraigo (despues) -> {}", cursoDesarraigo);
			}
		}
		log.debug("CursoDesarraigos -> {}", cursoDesarraigos);
		List<Long> cursoDesarraigoIds = cursoDesarraigos.stream()
				.filter(cursoDesarraigo -> cursoDesarraigo.getVersion() == -1)
				.map(cursoDesarraigo -> cursoDesarraigo.getCursoDesarraigoId()).collect(Collectors.toList());
		log.debug("cursoDesarraigoIds -> {}", cursoDesarraigoIds);
		for (Long cursoDesarraigoId : cursoDesarraigoIds) {
			cursoDesarraigoService.delete(cursoDesarraigoId);
		}
		cursoDesarraigos = cursoDesarraigoService.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
		log.debug("CursoDesarraigos -> {}", cursoDesarraigos);
		//
		List<BigDecimal> values = indiceAntiguedad(legajoId, anho, mes);
		BigDecimal porcentajeDocente = values.get(0);
		for (CursoDesarraigo cursoDesarraigo : cursoDesarraigos) {
			cursoDesarraigo.setVersion(0);
		}
		cursoDesarraigos = cursoDesarraigoService.saveAll(cursoDesarraigos);
		cursoCargos = cursoCargoService.findAllByLegajoDesarraigo(legajoId, anho, mes);
		log.debug("Cursos -> {}", cursoCargos);
		List<Long> ids = cursoCargos.stream().map(curso -> curso.getCursoId()).collect(Collectors.toList());
		Map<Long, Curso> cursos = cursoService.findAllByCursoIdIn(ids).stream()
				.collect(Collectors.toMap(Curso::getCursoId, curso -> curso));
		Persona persona = personaService.findByLegajoId(legajoId);
		List<CursoDesarraigo> desarraigosNew = new ArrayList<CursoDesarraigo>();
		for (CursoCargo cursoCargo : cursoCargos) {
			Curso curso = cursos.get(cursoCargo.getCursoId());
			Geografica geografica = curso.getGeografica();
			if (persona.getReemplazoDesarraigo() == 1) {
				geografica = geograficaService.findByGeograficaId(geografica.getGeograficaIdReemplazo());
			}
			BigDecimal desarraigo = geografica.getDesarraigo();
			if (persona.getMitadDesarraigo() == 1) {
				desarraigo = desarraigo.divide(new BigDecimal(2), 2, RoundingMode.HALF_UP);
			}
			if (curso.getAnual() == 0) {
				desarraigo = desarraigo.divide(new BigDecimal(2), 2, RoundingMode.HALF_UP);
			}
			log.debug("Desarraigo -> {}", desarraigo);
			Long cursoDesarraigoId = null;
			try {
				cursoDesarraigoId = cursoDesarraigoService.findByUnique(legajoId, anho, mes, curso.getCursoId())
						.getCursoDesarraigoId();
			} catch (CursoDesarraigoNotFoundException e) {
				log.debug("Sin Desarraigo");
			}
			BigDecimal factor = porcentajeDocente.add(BigDecimal.ONE);
			desarraigo = desarraigo.multiply(factor).setScale(0, RoundingMode.DOWN);
			desarraigosNew.add(new CursoDesarraigo(cursoDesarraigoId, legajoId, anho, mes, cursoCargo.getCursoId(),
					curso.getGeograficaId(), desarraigo, 1, curso, persona, geografica));
		}
		cursoDesarraigoService.saveAll(desarraigosNew);
	}

	@Transactional
	public void duplicarByLegajo(Long legajoId, Integer anho, Integer mes) {
		Periodo periodo = Periodo.prevMonth(anho, mes);
		Integer anhoAnterior = periodo.getAnho();
		Integer mesAnterior = periodo.getMes();
		List<CursoCargo> cursoCargos = new ArrayList<CursoCargo>();
		for (CursoCargo cursoCargo : cursoCargoService.findAllByLegajo(legajoId, anhoAnterior, mesAnterior)) {
			cursoCargos.add(new CursoCargo(null, cursoCargo.getCursoId(), anho, mes, cursoCargo.getCargoTipoId(),
					legajoId, cursoCargo.getHorasSemanales(), cursoCargo.getHorasTotales(),
					cursoCargo.getDesignacionTipoId(), cursoCargo.getCategoriaId(), cursoCargo.getDesarraigo(), null,
					cursoCargo.getCurso(), cursoCargo.getCargoTipo(), cursoCargo.getPersona(),
					cursoCargo.getDesignacionTipo(), cursoCargo.getCategoria()));
		}
		cursoCargos = cursoCargoService.saveall(cursoCargos);
	}

	@Transactional
	public void deleteZombies(Integer anho, Integer mes) {
		// Legajos de fusionados
		List<Long> fusionados = cursoFusionService.findAllByAnhoAndMes(anho, mes).stream()
				.map(fusion -> fusion.getLegajoId()).distinct().collect(Collectors.toList());
		// Legajos de cursoCargos
		List<Long> cursoCargos = cursoCargoService.findAllByAnhoAndMes(anho, mes).stream()
				.map(curso -> curso.getLegajoId()).distinct().collect(Collectors.toList());
		// Legajos zombies
		List<Long> zombies = fusionados.stream().filter(legajo -> !cursoCargos.contains(legajo))
				.collect(Collectors.toList());
		cursoFusionService.deleteAllByLegajoIdInAndAnhoAndMes(zombies, anho, mes);
	}

	public List<BigDecimal> indiceAntiguedad(Long legajoId, Integer anho, Integer mes) {
		Antiguedad antiguedad = null;
		try {
			antiguedad = antiguedadService.findByUnique(legajoId, anho, mes);
		} catch (AntiguedadNotFoundException e) {
			antiguedadService.calculate(legajoId, anho, mes);
			antiguedad = antiguedadService.findByUnique(legajoId, anho, mes);
		}

		Integer mesesDocentes = antiguedad.getMesesDocentes();
		Integer mesesAdministrativos = antiguedad.getMesesAdministrativos();

		AntiguedadLimite antiguedadLimite = null;
		try {
			antiguedadLimite = antiguedadLimiteService.findByMeses(mesesDocentes);
		} catch (AntiguedadLimiteNotFoundException e) {
			antiguedadLimite = new AntiguedadLimite();
		}
		Integer anhosAntiguedad = mesesAdministrativos / 12;
		if (anhosAntiguedad > 30)
			anhosAntiguedad = 30;

		List<BigDecimal> values = new ArrayList<BigDecimal>();
		values.add(new BigDecimal(antiguedadLimite.getPorcentaje()).divide(new BigDecimal(100)));
		values.add(new BigDecimal(anhosAntiguedad));
		return values;
	}

	public Integer calcularCategoriaId(Integer facultadId, Integer designacionTipoId, Integer cargoTipoId, Byte anual,
			Byte semestral, Boolean aplicaExcepcion) {
		Byte anualLocal = 0;
		Byte semestralLocal = 0;

		if (aplicaExcepcion)
			if (excepcionales.contains(facultadId))
				anualLocal = 1;
			else {
				if (anual == 1)
					anualLocal = 1;
				if (semestral == 1)
					semestralLocal = 1;
			}
		else {
			if (anual == 1)
				anualLocal = 1;
			if (semestral == 1)
				semestralLocal = 1;
		}

		Designacion designacion = null;
		try {
			designacion = designacionService.findByDesignacionTipoIdAndCargoTipoIdAndAnualAndSemestral(
					designacionTipoId, cargoTipoId, anualLocal, semestralLocal);
		} catch (DesignacionNotFoundException e) {
			return null;
		}
		return designacion.getCategoriaId();
	}

}
