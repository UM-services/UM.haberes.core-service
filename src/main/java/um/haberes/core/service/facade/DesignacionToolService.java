/**
 *
 */
package um.haberes.core.service.facade;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.AntiguedadLimiteException;
import um.haberes.core.exception.AntiguedadException;
import um.haberes.core.exception.CursoDesarraigoException;
import um.haberes.core.exception.DesignacionException;
import um.haberes.core.exception.LegajoControlException;
import um.haberes.core.kotlin.model.*;
import um.haberes.core.service.AntiguedadLimiteService;
import um.haberes.core.service.AntiguedadService;
import um.haberes.core.service.CursoCargoService;
import um.haberes.core.service.CursoDesarraigoService;
import um.haberes.core.service.CursoFusionService;
import um.haberes.core.service.CursoService;
import um.haberes.core.service.DesignacionService;
import um.haberes.core.service.DesignacionTipoService;
import um.haberes.core.service.GeograficaService;
import um.haberes.core.service.LegajoControlService;
import um.haberes.core.service.PersonaService;
import um.haberes.core.util.Periodo;
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

    private final CursoCargoService cursoCargoService;

    private final DesignacionTipoService designacionTipoService;

    private final CursoService cursoService;

    private final DesignacionService designacionService;

    private final LegajoControlService legajoControlService;

    private final CursoFusionService cursoFusionService;

    private final AntiguedadService antiguedadService;

    private final AntiguedadLimiteService antiguedadLimiteService;

    private final CursoDesarraigoService cursoDesarraigoService;

    private final PersonaService personaService;

    private final GeograficaService geograficaService;

    @Autowired
    public DesignacionToolService(CursoCargoService cursoCargoService, DesignacionTipoService designacionTipoService, CursoService cursoService, DesignacionService designacionService,
                                  LegajoControlService legajoControlService, CursoFusionService cursoFusionService, AntiguedadService antiguedadService, AntiguedadLimiteService antiguedadLimiteService,
                                  CursoDesarraigoService cursoDesarraigoService, PersonaService personaService, GeograficaService geograficaService) {
        this.cursoCargoService = cursoCargoService;
        this.designacionTipoService = designacionTipoService;
        this.cursoService = cursoService;
        this.designacionService = designacionService;
        this.legajoControlService = legajoControlService;
        this.cursoFusionService = cursoFusionService;
        this.antiguedadService = antiguedadService;
        this.antiguedadLimiteService = antiguedadLimiteService;
        this.cursoDesarraigoService = cursoDesarraigoService;
        this.personaService = personaService;
        this.geograficaService = geograficaService;
    }

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
        Integer[] nivelIds = {const_Nivel_Grado, const_Nivel_Tecnicatura};
        List<CursoCargo> cursoCargos = cursoCargoService.findAllByLegajoAndNivelIds(legajoId, anho, mes, Arrays.asList(nivelIds));
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
        Integer[] nivelIds = {const_Nivel_Grado, const_Nivel_Tecnicatura};
        log.debug("Fusionar Grado By Legajo");
        LegajoControl legajoControl = null;
        try {
            legajoControl = legajoControlService.findByUnique(legajoId, anho, mes);
        } catch (LegajoControlException e) {
            legajoControl = new LegajoControl();
        }
        if (legajoControl.getFusionado() == 1)
            return;
        log.debug("{}", legajoControl);

        if (cursoCargos == null) {
            cursoCargos = cursoCargoService.findAllByLegajoAndNivelIds(legajoId, anho, mes, Arrays.asList(nivelIds));
        }
        log.debug("CursoCargos -> {}", cursoCargos);

        // Delete all cursoFusion by legajo_id and month
        cursoFusionService.deleteAllByLegajoIdAndPeriodo(legajoId, anho, mes);

        // Toma las facultades de acuerdo a los cargos
        for (Facultad facultad : cursoCargos.stream().map(cursoCargo -> cursoCargo.getCurso().getFacultad())
                .collect(Collectors.toMap(Facultad::getFacultadId, Function.identity(),
                        (facultad, replacement) -> facultad))
                .values().stream().toList()) {
            log.debug("Fusionar Grado Facultad -> {}", facultad);
            // Toma las sedes de acuerdo a los cargos
            for (Geografica geografica : cursoCargos.stream()
                    .filter(cursoCargo -> cursoCargo.getCurso().getFacultadId() == facultad.getFacultadId())
                    .map(cursoCargo -> cursoCargo.getCurso().getGeografica())
                    .collect(Collectors.toMap(Geografica::getGeograficaId, Function.identity(),
                            (geografica, replacement) -> geografica))
                    .values().stream().toList()) {
//				cursoFusionService.deleteAllByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(legajoId, anho, mes,
//						facultad.getFacultadId(), geografica.getGeograficaId());
                log.debug("Fusionar Grado Geografica -> {}", geografica);
                // Toma los cargos de acuerdo a facultad y sede
                for (CargoTipo cargoTipo : cursoCargos.stream()
                        .filter(cursoCargo -> cursoCargo.getCurso().getFacultadId() == facultad.getFacultadId()
                                && cursoCargo.getCurso().getGeograficaId() == geografica.getGeograficaId())
                        .map(CursoCargo::getCargoTipo)
                        .collect(Collectors.toMap(CargoTipo::getCargoTipoId, Function.identity(),
                                (cargoTipo, replacement) -> cargoTipo))
                        .values().stream().toList()) {
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
        } catch (LegajoControlException e) {
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
    public void fusionarGradoByFacultadAnual(Long legajoId, Integer anho, Integer mes, Integer facultadId,
                                             Integer geograficaId, Integer cargoTipoId, List<CursoCargo> cursoCargos, Boolean aplicaExcepcion) {
        log.debug("Fusionar Grado por Facultad Anual");
        Integer totalSimplesAnual = 0;
        for (CursoCargo cursoCargo : cursoCargos.stream().filter(curso -> curso.getCurso().getAnual() == 1)
                .toList()) {
            totalSimplesAnual += cursoCargo.getDesignacionTipo().getSimples();
        }
        if (aplicaExcepcion) {
            if (excepcionales.contains(facultadId)) {
                for (CursoCargo cursoCargo : cursoCargos.stream().filter(curso -> curso.getCurso().getSemestre1() == 1)
                        .toList()) {
                    totalSimplesAnual += cursoCargo.getDesignacionTipo().getSimples();
                }
                for (CursoCargo cursoCargo : cursoCargos.stream().filter(curso -> curso.getCurso().getSemestre2() == 1)
                        .toList()) {
                    totalSimplesAnual += cursoCargo.getDesignacionTipo().getSimples();
                }
            }
        }
        if (totalSimplesAnual == 0)
            return;
        List<Integer> designaciones = this.listOfDesignaciones(totalSimplesAnual);

        List<CursoFusion> fusiones = new ArrayList<>();
        for (Integer designacionTipoId : designaciones) {
            fusiones.add(new CursoFusion(null, legajoId, anho, mes, facultadId, geograficaId, cargoTipoId,
                    designacionTipoId, (byte) 1, this.calcularCategoriaId(facultadId, designacionTipoId, cargoTipoId,
                    (byte) 1, (byte) 0, aplicaExcepcion),
                    null, null, null, null, null, null));
        }
        fusiones = cursoFusionService.saveAll(fusiones);
    }

    @Transactional
    public void fusionarGradoByFacultadSemestral(Long legajoId, Integer anho, Integer mes, Integer facultadId,
                                                 Integer geograficaId, Integer cargoTipoId, List<CursoCargo> cursoCargos) {
        log.debug("Fusionar Grado por Facultad Semestral");
        Integer totalSimplesSemestre1 = 0;
        for (CursoCargo cursoCargo : cursoCargos.stream().filter(curso -> curso.getCurso().getSemestre1() == 1)
                .toList()) {
            totalSimplesSemestre1 += cursoCargo.getDesignacionTipo().getSimples();
        }
        Integer totalSimplesSemestre2 = 0;
        for (CursoCargo cursoCargo : cursoCargos.stream().filter(curso -> curso.getCurso().getSemestre2() == 1)
                .toList()) {
            totalSimplesSemestre2 += cursoCargo.getDesignacionTipo().getSimples();
        }
        int totalSimplesSemestral = totalSimplesSemestre1 + totalSimplesSemestre2;
        if (totalSimplesSemestral == 0)
            return;

        List<Integer> designaciones = this.listOfDesignaciones(totalSimplesSemestral);

        List<CursoFusion> fusiones = new ArrayList<>();
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
        // delete CursoDesarraigo that does not exist anymore
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
                        curso -> curso.getLegajoId().toString() + "." + curso.getAnho() + "."
                                + curso.getMes().toString() + "." + curso.getCursoId().toString(),
                        cursoDesarraigo -> cursoDesarraigo));
        log.debug("CursoDesarraigoMap -> {}", cursoDesarraigoMap);
        for (CursoCargo cursoCargo : cursoCargos) {
            log.debug("CursoCargo -> {}", cursoCargo);
            String key = cursoCargo.getLegajoId().toString() + "." + cursoCargo.getAnho() + "."
                    + cursoCargo.getMes() + "." + cursoCargo.getCursoId().toString();
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
                .map(CursoDesarraigo::getCursoDesarraigoId).collect(Collectors.toList());
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
        List<Long> ids = cursoCargos.stream().map(CursoCargo::getCursoId).collect(Collectors.toList());
        Map<Long, Curso> cursos = cursoService.findAllByCursoIdIn(ids).stream()
                .collect(Collectors.toMap(Curso::getCursoId, curso -> curso));
        Persona persona = personaService.findByLegajoId(legajoId);
        List<CursoDesarraigo> desarraigosNew = new ArrayList<>();
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
            } catch (CursoDesarraigoException e) {
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
        List<CursoCargo> cursoCargos = new ArrayList<>();
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
                .map(CursoFusion::getLegajoId).distinct().toList();
        // Legajos de cursoCargos
        List<Long> cursoCargos = cursoCargoService.findAllByAnhoAndMes(anho, mes).stream()
                .map(CursoCargo::getLegajoId).distinct().toList();
        // Legajos zombies
        List<Long> zombies = fusionados.stream().filter(legajo -> !cursoCargos.contains(legajo))
                .collect(Collectors.toList());
        cursoFusionService.deleteAllByLegajoIdInAndAnhoAndMes(zombies, anho, mes);
    }

    public List<BigDecimal> indiceAntiguedad(Long legajoId, Integer anho, Integer mes) {
        Antiguedad antiguedad = null;
        try {
            antiguedad = antiguedadService.findByUnique(legajoId, anho, mes);
        } catch (AntiguedadException e) {
            antiguedadService.calculate(legajoId, anho, mes);
            antiguedad = antiguedadService.findByUnique(legajoId, anho, mes);
        }

        int mesesDocentes = antiguedad.getMesesDocentes();
        int mesesAdministrativos = antiguedad.getMesesAdministrativos();

        AntiguedadLimite antiguedadLimite = null;
        try {
            antiguedadLimite = antiguedadLimiteService.findByMeses(mesesDocentes);
        } catch (AntiguedadLimiteException e) {
            antiguedadLimite = new AntiguedadLimite();
        }
        int anhosAntiguedad = mesesAdministrativos / 12;
        if (anhosAntiguedad > 30)
            anhosAntiguedad = 30;

        List<BigDecimal> values = new ArrayList<>();
        values.add(new BigDecimal(antiguedadLimite.getPorcentaje()).setScale(2, RoundingMode.HALF_UP).divide(new BigDecimal(100), RoundingMode.HALF_UP));
        values.add(new BigDecimal(anhosAntiguedad));
        log.debug("Values -> {}", values);
        return values;
    }

    public Integer calcularCategoriaId(Integer facultadId, Integer designacionTipoId, Integer cargoTipoId, Byte anual,
                                       Byte semestral, Boolean aplicaExcepcion) {
        byte anualLocal = 0;
        byte semestralLocal = 0;

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
        log.debug("DesignacionTipoId={}", designacionTipoId);
        log.debug("CargoTipoId={}", cargoTipoId);
        log.debug("AnualLocal={}", anualLocal);
        log.debug("SemestralLocal={}", semestralLocal);
        try {
            designacion = designacionService.findByDesignacionTipoIdAndCargoTipoIdAndAnualAndSemestral(
                    designacionTipoId, cargoTipoId, anualLocal, semestralLocal);
        } catch (DesignacionException e) {
            return null;
        }
        return designacion.getCategoriaId();
    }

    private List<Integer> listOfDesignaciones(int totalSimples) {
        return switch (totalSimples) {
            case 1 -> List.of(const_Simple);
            case 2 -> List.of(const_Intermedia);
            case 3 -> List.of(const_Especial);
            case 4 -> List.of(const_Especial, const_Simple);
            case 5 -> List.of(const_Especial, const_Intermedia);
            case 6 -> List.of(const_Extraordinaria);
            case 7 -> List.of(const_Extraordinaria, const_Simple);
            case 8 -> List.of(const_Extraordinaria, const_Intermedia);
            case 9 -> List.of(const_Extraordinaria, const_Especial);
            case 10 -> List.of(const_Extraordinaria, const_Especial, const_Simple);
            case 11 -> List.of(const_Extraordinaria, const_Especial, const_Intermedia);
            case 12 -> List.of(const_Extraordinaria, const_Extraordinaria);
            case 13 -> List.of(const_Extraordinaria, const_Extraordinaria, const_Simple);
            case 14 -> List.of(const_Extraordinaria, const_Extraordinaria, const_Intermedia);
            case 15 -> List.of(const_Extraordinaria, const_Extraordinaria, const_Especial);
            default -> null;
        };
    }

}
