/**
 *
 */
package um.haberes.core.service.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.exception.*;
import um.haberes.core.kotlin.model.*;
import um.haberes.core.kotlin.model.view.NovedadDuplicada;
import um.haberes.core.service.*;
import um.haberes.core.service.view.NovedadDuplicadaService;
import um.haberes.core.util.Periodo;
import um.haberes.core.util.Tool;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author daniel
 */
@Service
@Slf4j
public class MakeLiquidacionService {

    private final int constLiquidarConFusion = 1;
    private final int constLiquidarSinFusion = 2;

    private final CargoLiquidacionService cargoLiquidacionService;

    private final DesignacionToolService designacionToolService;

    private final CargoClaseDetalleService cargoClaseDetalleService;

    private final ControlService controlService;

    private final PersonaService personaService;

    private final NovedadService novedadService;

    private final ItemService itemService;

    private final LegajoControlService legajoControlService;

    private final CodigoGrupoService codigoGrupoService;

    private final CursoDesarraigoService cursoDesarraigoService;

    private final CursoFusionService cursoFusionService;

    private final DependenciaService dependenciaService;

    private final CategoriaService categoriaService;

    private final AntiguedadService antiguedadService;

    private final LiquidacionService liquidacionService;

    private final ActividadService actividadService;

    private final LetraService letraService;

    private final CargoService cargoService;

    private final NovedadDuplicadaService novedadDuplicadaService;

    private final CursoCargoService cursoCargoService;

    private final CodigoService codigoService;

    private final AdicionalCursoTablaService adicionalCursoTablaService;

    private final CategoriaPeriodoService categoriaPeriodoService;

    private final LiquidacionAdicionalService liquidacionAdicionalService;

    private Control control;
    private Persona persona;
    private List<BigDecimal> indices;
    private Map<Integer, Codigo> codigos;
    private Map<Integer, Item> items;
    private Map<Integer, Novedad> novedades;

    @Autowired
    public MakeLiquidacionService(CargoLiquidacionService cargoLiquidacionService, DesignacionToolService designacionToolService, CargoClaseDetalleService cargoClaseDetalleService, ControlService controlService,
                                  PersonaService personaService, NovedadService novedadService, ItemService itemService, LegajoControlService legajoControlService, CodigoGrupoService codigoGrupoService,
                                  CursoDesarraigoService cursoDesarraigoService, CursoFusionService cursoFusionService, DependenciaService dependenciaService, CategoriaService categoriaService,
                                  AntiguedadService antiguedadService, LiquidacionService liquidacionService, ActividadService actividadService, LetraService letraService, CargoService cargoService,
                                  NovedadDuplicadaService novedadDuplicadaService, CursoCargoService cursoCargoService, CodigoService codigoService, AdicionalCursoTablaService adicionalCursoTablaService,
                                  CategoriaPeriodoService categoriaPeriodoService, LiquidacionAdicionalService liquidacionAdicionalService) {
        this.cargoLiquidacionService = cargoLiquidacionService;
        this.designacionToolService = designacionToolService;
        this.cargoClaseDetalleService = cargoClaseDetalleService;
        this.controlService = controlService;
        this.personaService = personaService;
        this.novedadService = novedadService;
        this.itemService = itemService;
        this.legajoControlService = legajoControlService;
        this.codigoGrupoService = codigoGrupoService;
        this.cursoDesarraigoService = cursoDesarraigoService;
        this.cursoFusionService = cursoFusionService;
        this.dependenciaService = dependenciaService;
        this.categoriaService = categoriaService;
        this.antiguedadService = antiguedadService;
        this.liquidacionService = liquidacionService;
        this.actividadService = actividadService;
        this.letraService = letraService;
        this.cargoService = cargoService;
        this.novedadDuplicadaService = novedadDuplicadaService;
        this.cursoCargoService = cursoCargoService;
        this.codigoService = codigoService;
        this.adicionalCursoTablaService = adicionalCursoTablaService;
        this.categoriaPeriodoService = categoriaPeriodoService;
        this.liquidacionAdicionalService = liquidacionAdicionalService;
    }

    @Transactional
    public void makeContext(Long legajoId, Integer anho, Integer mes) {
        this.deleteNovedadDuplicada(anho, mes);
        indices = designacionToolService.indiceAntiguedad(legajoId, anho, mes);
        persona = personaService.findByLegajoId(legajoId);
        control = controlService.findByPeriodo(anho, mes);
        LegajoControl legajoControl;
        try {
            legajoControlService.findByUnique(legajoId, anho, mes);
        } catch (LegajoControlException e) {
            legajoControl = new LegajoControl(null, legajoId, anho, mes, (byte) 0, (byte) 0, (byte) 0, null);
            legajoControlService.add(legajoControl);
        }
        codigos = codigoService.findAll().stream().collect(Collectors.toMap(Codigo::getCodigoId, codigo -> codigo));
        novedades = novedadService.findAllByLegajo(legajoId, anho, mes).stream().filter(novedad -> novedad.getCodigoId() != 980).collect(Collectors.toMap(Novedad::getCodigoId, novedad -> novedad));
        items = new HashMap<>();
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
                log.debug("Nothing to do");
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
                log.debug("Nothing to do");
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
                log.debug("Nothing to do");
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
            boolean liquidable = !cargoLiquidacionService.findAllActivosByLegajo(legajoId, anho, mes).isEmpty();
            if (!cargoClaseDetalleService.findAllByLegajo(legajoId, anho, mes).isEmpty()) {
                liquidable = true;
            }
            if (!liquidable) {
                return;
            }
        }
        BigDecimal coeficienteLiquidacion = BigDecimal.ONE;
        if (mes == 6 || mes == 12) {
            coeficienteLiquidacion = new BigDecimal("1.5");
        }

        // Calcula basico y antiguedad sobre cargoLiquidacions
        calculaBasicoAndAntiguedad(legajoId, anho, mes);

        // Incentivo Posgrado
        BigDecimal incentivoPosgrado = switch (persona.getPosgrado()) {
            case 1 -> control.getDoctorado();
            case 2 -> control.getMaestria();
            case 3 -> control.getEspecializacion();
            default -> BigDecimal.ZERO;
        };
        if (incentivoPosgrado.compareTo(BigDecimal.ZERO) > 0) {
            Integer codigoId = 9;
            this.addItem(legajoId, anho, mes, codigoId, incentivoPosgrado);
        }

        // Remunerativos
        for (CodigoGrupo codigoGrupo : codigoGrupos.stream().filter(c -> c.getRemunerativo() == 1).toList()) {
            Integer codigoId = codigoGrupo.getCodigoId();
            if (novedades.containsKey(codigoId)) {
                BigDecimal value = novedades.get(codigoId).getImporte();
                this.addItem(legajoId, anho, mes, codigoId, value);
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
                items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(), BigDecimal.ZERO, null, null));
            }
            Item item = items.get(codigoId);
            item.setImporte(item.getImporte().add(totalDesarraigo).setScale(2, RoundingMode.HALF_UP));
        }

        // Calcula basico y antiguedad sobre cargos con clase
        for (CargoClaseDetalle detalle : cargoClaseDetalleService.findAllByLegajo(legajoId, anho, mes)) {
            BigDecimal basico = detalle.getValorHora().multiply(new BigDecimal(detalle.getHoras())).setScale(2, RoundingMode.HALF_UP);
            int codigoId = 1;
            this.addItem(legajoId, anho, mes, codigoId, basico);

            BigDecimal antiguedad = basico.multiply(indices.get(0)).setScale(2, RoundingMode.HALF_UP);
            codigoId = 2;
            this.addItem(legajoId, anho, mes, codigoId, antiguedad);
        }

        // Aguinaldo
        BigDecimal aguinaldo = BigDecimal.ZERO;
        if (novedades.containsKey(3)) {
            aguinaldo = novedades.get(3).getImporte();
        }

        // Carga de Novedades No Remunerativas
        for (CodigoGrupo codigoGrupo : codigoGrupos.stream().filter(c -> c.getNoRemunerativo() == 1).toList()) {
            Integer codigoId = codigoGrupo.getCodigoId();
            if (novedades.containsKey(codigoId)) {
                BigDecimal value = novedades.get(codigoId).getImporte();
                this.addItem(legajoId, anho, mes, codigoId, value);
            }
        }

        // Remunerativos
        BigDecimal totalRemunerativo = BigDecimal.ZERO;
        Integer codigoId = 96;
        if (!items.containsKey(codigoId)) {
            items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(), BigDecimal.ZERO, null, null));
        }
        BigDecimal value = BigDecimal.ZERO;
        for (CodigoGrupo codigoGrupo : codigoGrupos.stream().filter(c -> c.getRemunerativo() == 1).toList()) {
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
        this.calculateNoRemunerativos(legajoId, anho, mes, codigoId, codigoGrupos);

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
        int diasInasistencia = 0;
        if (novedades.containsKey(18)) {
            diasInasistencia = novedades.get(18).getImporte().intValue();
            BigDecimal inasistencias = new BigDecimal(-1).multiply(new BigDecimal(diasInasistencia).multiply(valorDia)).setScale(2, RoundingMode.HALF_UP);
            log.debug("Descuento Inasistencias -> {}", inasistencias);
            codigoId = 18;
            if (!items.containsKey(codigoId)) {
                items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(), BigDecimal.ZERO, null, null));
            }
            item = items.get(codigoId);
            item.setImporte(inasistencias.setScale(2, RoundingMode.HALF_UP));
        }

        // Licencia maternidad
        if (novedades.containsKey(30)) {
            diasInasistencia = novedades.get(30).getImporte().intValue();
            log.debug("Días Inasistencia -> {}", diasInasistencia);
            if (persona.getEstado() == 4 && diasInasistencia > 0) {
                BigDecimal licenciaMaternidad = new BigDecimal(-1).multiply(new BigDecimal(diasInasistencia).multiply(valorDia)).setScale(2, RoundingMode.HALF_UP);
                codigoId = 30;
                this.addItem(legajoId, anho, mes, codigoId, licenciaMaternidad);
            }
        }

        // Remunerativos
        codigoId = 96;
        if (!items.containsKey(codigoId)) {
            items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(), BigDecimal.ZERO, null, null));
        }
        value = BigDecimal.ZERO;
        for (CodigoGrupo codigoGrupo : codigoGrupos.stream().filter(c -> c.getRemunerativo() == 1).toList()) {
            if (items.containsKey(codigoGrupo.getCodigoId())) {
                value = value.add(items.get(codigoGrupo.getCodigoId()).getImporte()).setScale(2, RoundingMode.HALF_UP);
            }
        }
        item = items.get(codigoId);
        item.setImporte(value.setScale(2, RoundingMode.HALF_UP));

        // No remunerativos
        codigoId = 97;
        this.calculateNoRemunerativos(legajoId, anho, mes, codigoId, codigoGrupos);

        // Jubilacion
        BigDecimal conAportes = items.get(96).getImporte();
        BigDecimal coeficiente = control.getJubilaem().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        if (conAportes.compareTo(coeficienteLiquidacion.multiply(control.getMaximo1sijp())) <= 0) {
            value = conAportes.multiply(coeficiente).setScale(2, RoundingMode.HALF_UP);
        } else {
            value = coeficienteLiquidacion.multiply(coeficiente).multiply(control.getMaximo1sijp()).setScale(2, RoundingMode.HALF_UP);
        }

        if (value.compareTo(BigDecimal.ZERO) != 0) {
            codigoId = 61;
            this.addItem(legajoId, anho, mes, codigoId, value);
        }

        // Jubilacion Secundario
        boolean onlyETEC = this.evaluateOnlyETEC(items);

        if (onlyETEC) {
            conAportes = items.get(96).getImporte();
            coeficiente = new BigDecimal("0.02");
            value = conAportes.multiply(coeficiente).setScale(2, RoundingMode.HALF_UP);

            if (value.compareTo(BigDecimal.ZERO) != 0) {
                codigoId = 70;
                this.addItem(legajoId, anho, mes, codigoId, value);
            }
        }

        // INSSJP
        coeficiente = control.getInssjpem().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        if (conAportes.compareTo(coeficienteLiquidacion.multiply(control.getMaximo5sijp())) <= 0) {
            value = conAportes.multiply(coeficiente).setScale(2, RoundingMode.HALF_UP);
        } else {
            value = coeficienteLiquidacion.multiply(coeficiente).multiply(control.getMaximo5sijp()).setScale(2, RoundingMode.HALF_UP);
        }
        if (persona.getEstadoAfip() == 2) {
            value = BigDecimal.ZERO;
        }

        if (value.compareTo(BigDecimal.ZERO) != 0) {
            codigoId = 62;
            this.addItem(legajoId, anho, mes, codigoId, value);
        }

        // Obra Social
//        coeficiente = control.getOsociaem().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
//        if (conAportes.compareTo(coeficienteLiquidacion.multiply(control.getMinimoAporte())) < 0) {
//            value = coeficiente.multiply(coeficienteLiquidacion.multiply(control.getMinimoAporte())).setScale(2, RoundingMode.HALF_UP);
//        }
//        if (conAportes.compareTo(coeficienteLiquidacion.multiply(control.getMinimoAporte())) >= 0 && conAportes.compareTo(coeficienteLiquidacion.multiply(control.getMaximoAporte())) <= 0) {
//            value = coeficiente.multiply(conAportes).setScale(2, RoundingMode.HALF_UP);
//        }
//        if (conAportes.compareTo(coeficienteLiquidacion.multiply(control.getMaximoAporte())) > 0) {
//            value = coeficiente.multiply(coeficienteLiquidacion.multiply(control.getMaximoAporte())).setScale(2, RoundingMode.HALF_UP);
//        }
        if (persona.getEstadoAfip() == 2 || conAportes.compareTo(BigDecimal.ZERO) == 0) {
            value = BigDecimal.ZERO;
        }

        if (value.compareTo(BigDecimal.ZERO) != 0) {
            codigoId = 63;
            this.addItem(legajoId, anho, mes, codigoId, value);
        }

        // Deducciones
        for (CodigoGrupo codigoGrupo : codigoGrupos.stream().filter(c -> c.getDeduccion() == 1).toList()) {
            codigoId = codigoGrupo.getCodigoId();
            if (novedades.containsKey(codigoId)) {
                value = novedades.get(codigoId).getImporte();
                if (value.compareTo(BigDecimal.ZERO) != 0) {
                    this.addItem(legajoId, anho, mes, codigoId, value);
                }
            }
        }

        // Total deducciones
        codigoId = 98;
        if (!items.containsKey(codigoId)) {
            items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(), BigDecimal.ZERO, null, null));
        }
        value = BigDecimal.ZERO;
        for (CodigoGrupo codigoGrupo : codigoGrupos.stream().filter(c -> c.getDeduccion() == 1).toList()) {
            if (items.containsKey(codigoGrupo.getCodigoId())) {
                value = value.add(items.get(codigoGrupo.getCodigoId()).getImporte()).setScale(2, RoundingMode.HALF_UP);
            }
        }
        item = items.get(codigoId);
        item.setImporte(item.getImporte().add(value).setScale(2, RoundingMode.HALF_UP));

        // Ajuste
        BigDecimal neto = items.get(96).getImporte().add(items.get(97).getImporte()).subtract(items.get(98).getImporte());
        BigDecimal centavos = neto.subtract(neto.setScale(0, RoundingMode.HALF_UP));
        if (centavos.compareTo(BigDecimal.ZERO) > 0) {
            value = centavos.abs();
            codigoId = 75;
            this.addItem(legajoId, anho, mes, codigoId, value);
            codigoId = 98;
            this.addItem(legajoId, anho, mes, codigoId, value);
        }
        if (centavos.compareTo(BigDecimal.ZERO) < 0) {
            value = centavos.abs();
            codigoId = 45;
            this.addItem(legajoId, anho, mes, codigoId, value);
            codigoId = 97;
            this.addItem(legajoId, anho, mes, codigoId, value);
        }

        // Neto
        value = items.get(96).getImporte().add(items.get(97).getImporte()).subtract(items.get(98).getImporte());
        codigoId = 99;
        if (!items.containsKey(codigoId)) {
            items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(), BigDecimal.ZERO, null, null));
        }
        item = items.get(codigoId);
        item.setImporte(item.getImporte().add(value));

        // Eliminar después de detectar la duplicación
        itemService.deleteAllByLegajo(legajoId, anho, mes);
        //
        itemService.saveAll(new ArrayList<>(items.values()));

        liquidacionService.add(new Liquidacion(null, legajoId, anho, mes, Tool.dateAbsoluteArgentina(), null, persona.getDependenciaId(), persona.getSalida(), items.get(96).getImporte(), items.get(97).getImporte(), items.get(98).getImporte(), items.get(99).getImporte(), (byte) 0, persona.getEstado(), persona.getLiquida(), null, null));

        try {
            actividadService.findByUnique(legajoId, anho, mes);
        } catch (ActividadException e) {
            actividadService.add(new Actividad(null, legajoId, anho, mes, (byte) 1, (byte) 1, (byte) 1, 10, null, null));
        }

        try {
            letraService.findByUnique(legajoId, anho, mes);
        } catch (LetraException e) {
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
                log.debug("Nothing to do");
            }
        }

        liquidacionAdicionalService.deleteAllByLegajo(legajoId, anho, mes);
        letraService.deleteByUnique(legajoId, anho, mes);
        itemService.deleteAllByLegajo(legajoId, anho, mes);
        liquidacionService.deleteByLegajo(legajoId, anho, mes);
        // Poner liquidado y bloqueado en legajoControl en 0
        LegajoControl legajoControl = legajoControlService.findByUnique(legajoId, anho, mes);
        legajoControl.setLiquidado((byte) 0);
        legajoControlService.update(legajoControl, legajoControl.getLegajoControlId());
    }

    @Transactional
    public void calculaBasicoAndAntiguedad(Long legajoId, Integer anho, Integer mes) {
        // Calcula sobre Cursos (Adicional)
        // cantidad de horas por dependencia
        Map<Integer, Integer> horasDependencia = new HashMap<>();
        for (CursoCargo cursoCargo : cursoCargoService.findAllByLegajoWithAdicional(legajoId, anho, mes)) {
            Dependencia dependencia = dependenciaService.findFirstByFacultadIdAndGeograficaId(Objects.requireNonNull(cursoCargo.getCurso()).getFacultadId(), cursoCargo.getCurso().getGeograficaId());
            if (!horasDependencia.containsKey(dependencia.getDependenciaId())) {
                horasDependencia.put(dependencia.getDependenciaId(), 0);
            }
            horasDependencia.replace(dependencia.getDependenciaId(), horasDependencia.get(dependencia.getDependenciaId()) + cursoCargo.getHorasSemanales().intValue());
        }
        log.debug("Horas Dependencia -> {}", horasDependencia);

        // calcula con fusión o sin fusión, depende la configuración del mes
        Map<Integer, BigDecimal> totalDependencia = null;
        if (control.getModoLiquidacionId() == constLiquidarConFusion) {
            // sumatoria de cursoFusion para cada dependencia
            totalDependencia = this.calculateTotalDependenciaFusionado(legajoId, anho, mes);
        }
        if (control.getModoLiquidacionId() == constLiquidarSinFusion) {
            // sumatoria de cursoCargo para cada dependencia
            totalDependencia = this.calculateTotalDependenciaSinFusion(legajoId, anho, mes);
        }
        log.debug("Total Dependencia -> {}", totalDependencia);

        // Calcula basico y antiguedad para cada dependencia considerando adicionales
        assert totalDependencia != null;
        for (Integer dependenciaId : totalDependencia.keySet()) {
            Dependencia dependencia = dependenciaService.findByDependenciaId(dependenciaId);
            AdicionalCursoTabla adicionalCursoTabla = null;
            try {
                adicionalCursoTabla = adicionalCursoTablaService.findByFacultadIdAndPeriodo(dependencia.getFacultadId(), anho, mes);
            } catch (AdicionalCursoTablaException e) {
                log.debug("Sin Tabla Adicional de Facultad");
                // Busco por facultad y geografica
                try {
                    adicionalCursoTabla = adicionalCursoTablaService.findByFacultadIdAndGeograficaIdAndPeriodo(dependencia.getFacultadId(), dependencia.getGeograficaId(), anho, mes);
                } catch (AdicionalCursoTablaException e1) {
                    log.debug("Sin Tabla Adicional de Facultad y Geografica");
                }
            }

            if (adicionalCursoTabla != null) {
                Integer horas = horasDependencia.get(dependenciaId);
                BigDecimal totalCategoria = totalDependencia.get(dependenciaId);
                BigDecimal porcentaje = BigDecimal.ZERO;
                for (AdicionalCursoRango adicionalCursoRango : Objects.requireNonNull(adicionalCursoTabla.getAdicionalCursoRangos())) {
                    if (horas >= adicionalCursoRango.getHorasDesde() && horas <= adicionalCursoRango.getHorasHasta()) {
                        porcentaje = adicionalCursoRango.getPorcentaje();
                    }
                }
                log.debug("Horas -> {} - Total -> {} - Porcentaje -> {}", horas, totalCategoria, porcentaje);
                BigDecimal adicional = totalCategoria.multiply(porcentaje).divide(new BigDecimal(100), RoundingMode.HALF_UP);
                if (adicional.compareTo(BigDecimal.ZERO) > 0) {
                    LiquidacionAdicional liquidacionAdicional = new LiquidacionAdicional(null, legajoId, anho, mes, dependenciaId, adicional, null, null);
                    liquidacionAdicionalService.add(liquidacionAdicional);
                    // Basico
                    int codigoId = 1;
                    this.addItem(legajoId, anho, mes, codigoId, adicional);

                    // Antigüedad
                    BigDecimal antiguedad = BigDecimal.ZERO;
                    antiguedad = adicional.multiply(indices.get(0)).setScale(2, RoundingMode.HALF_UP);
                    codigoId = 2;
                    if (antiguedad.compareTo(BigDecimal.ZERO) != 0) {
                        this.addItem(legajoId, anho, mes, codigoId, antiguedad);
                    }

                }
            }
        }

        // Calcula sobre Cargos
        for (CargoLiquidacion cargoLiquidacion : cargoLiquidacionService.findAllByLegajo(legajoId, anho, mes)) {
            BigDecimal valorMes = BigDecimal.ZERO;

            // Basico
            BigDecimal basico = cargoLiquidacion.getCategoriaBasico().multiply(new BigDecimal(cargoLiquidacion.getJornada())).setScale(2, RoundingMode.HALF_UP);
            BigDecimal horas = BigDecimal.ONE;
            BigDecimal estadoDocenteNuevo;
            if (cargoLiquidacion.getHorasJornada().compareTo(BigDecimal.ZERO) > 0) {
                horas = cargoLiquidacion.getHorasJornada();
            }
            basico = basico.multiply(horas).setScale(2, RoundingMode.HALF_UP);
            Categoria categoria = cargoLiquidacion.getCategoria();
            assert categoria != null;
            estadoDocenteNuevo = categoria.getEstadoDocente();
            if (estadoDocenteNuevo.compareTo(BigDecimal.ZERO) != 0) {
                int codigoId = 4;
                this.addItem(legajoId, anho, mes, codigoId, estadoDocenteNuevo);
            }

            if (categoria.getBasico().compareTo(basico) > 0) {
                basico = categoria.getBasico();
            }
            valorMes = valorMes.add(basico).setScale(2, RoundingMode.HALF_UP);
            valorMes = valorMes.add(estadoDocenteNuevo).setScale(2, RoundingMode.HALF_UP);
            int codigoId = 1;
            Item item;
            if (basico.compareTo(BigDecimal.ZERO) != 0) {
                if (!items.containsKey(codigoId)) {
                    items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(), BigDecimal.ZERO, null, null));
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
                antiguedad = basico.multiply(indices.getFirst()).setScale(2, RoundingMode.HALF_UP);
            }
            valorMes = valorMes.add(antiguedad).setScale(2, RoundingMode.HALF_UP);
            codigoId = 2;
            if (antiguedad.compareTo(BigDecimal.ZERO) != 0) {
                this.addItem(legajoId, anho, mes, codigoId, antiguedad);
            }

            // Calcula estado docente
            BigDecimal estadoDocente = BigDecimal.ZERO;
            if (cargoLiquidacion.getCategoriaId() != null) {
                estadoDocente = this.calculateEstadoDocente(cargoLiquidacion.getCategoriaId());
            }

            if (basico.compareTo(BigDecimal.ZERO) == 0) estadoDocente = basico;

            valorMes = valorMes.add(estadoDocente).setScale(2, RoundingMode.HALF_UP);

            if (estadoDocente.compareTo(BigDecimal.ZERO) != 0) {
                codigoId = 4;
                this.addItem(legajoId, anho, mes, codigoId, estadoDocente);
            }

            // Presentismo
            BigDecimal presentismoSi = BigDecimal.ZERO;
            BigDecimal presentismoNo = BigDecimal.ZERO;

            if (cargoLiquidacion.getCategoria().getNoDocente() == 1) {
                presentismoSi = basico.multiply(new BigDecimal(cargoLiquidacion.getPresentismo()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
                codigoId = 16;
                this.addItem(legajoId, anho, mes, codigoId, presentismoSi);
            }
            valorMes = valorMes.add(presentismoSi);

            codigoId = 16;
            if (novedades.containsKey(codigoId)) {
                if (novedades.get(codigoId).getValue().equals("N")) {
                    presentismoNo = basico.multiply(new BigDecimal(cargoLiquidacion.getPresentismo()).divide(new BigDecimal(-100), 2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
                    codigoId = 17;
                    this.addItem(legajoId, anho, mes, codigoId, presentismoNo);
                }
            }

            // Horas extras
            codigoId = 19;
            if (novedades.containsKey(codigoId)) {
                int totalHoras = cargoLiquidacion.getJornada() == 1 ? 100 : 180;
                BigDecimal unaHoraTrabajada = valorMes.divide(new BigDecimal(totalHoras), 2, RoundingMode.HALF_UP);
                BigDecimal horasExtras = unaHoraTrabajada.multiply(novedades.get(codigoId).getImporte()).setScale(2, RoundingMode.HALF_UP);
                this.addItem(legajoId, anho, mes, codigoId, horasExtras);
            }
        }
    }

    @Transactional
    public void generateCargosDocentes(Long legajoId, Integer anho, Integer mes) {
        cargoLiquidacionService.deleteAllCargosDocentes(legajoId, anho, mes);
        Map<String, Dependencia> dependencias = dependenciaService.findAll().stream().collect(Collectors.toMap(Dependencia::getSedeKey, Function.identity(), (dependencia, replacement) -> dependencia));
        List<CargoLiquidacion> cargoLiquidacions = new ArrayList<>();
        for (Cargo cargo : cargoService.findAllDocenteByPeriodo(legajoId, anho, mes)) {
            logCargo(cargo);
            Categoria categoria = cargo.getCategoria();
            if (categoria.getCategoriaId() != 980) {
                cargoLiquidacions.add(new CargoLiquidacion(null,
                        legajoId,
                        anho, mes,
                        cargo.getDependenciaId(),
                        Periodo.firstDay(anho, mes),
                        Periodo.lastDay(anho, mes),
                        categoria.getCategoriaId(),
                        categoria.getNombre(),
                        categoria.getBasico(),
                        categoria.getEstadoDocente(),
                        cargo.getHorasJornada(),
                        cargo.getJornada(),
                        cargo.getPresentismo(),
                        "A",
                        null,
                        null,
                        categoria));
            }
        }
        control = controlService.findByPeriodo(anho, mes);
        if (control.getModoLiquidacionId() == constLiquidarConFusion) {
            for (CursoFusion cursoFusion : cursoFusionService.findAllByLegajoId(legajoId, anho, mes)) {
                Dependencia dependencia = dependencias.get(cursoFusion.getFacultadId() + "." + cursoFusion.getGeograficaId());
                Categoria categoria = cursoFusion.getCategoria();
                cargoLiquidacions.add(new CargoLiquidacion(null,
                        legajoId,
                        anho,
                        mes,
                        dependencia.getDependenciaId(),
                        Periodo.firstDay(anho, mes),
                        Periodo.lastDay(anho, mes),
                        categoria.getCategoriaId(),
                        categoria.getNombre(),
                        categoria.getBasico(),
                        categoria.getEstadoDocente(),
                        BigDecimal.ZERO,
                        1,
                        0,
                        "A",
                        null,
                        dependencia,
                        categoria));
            }
        }
        if (control.getModoLiquidacionId() == constLiquidarSinFusion) {
            for (CursoCargo cursoCargo : cursoCargoService.findAllByLegajo(legajoId, anho, mes)) {
                if (cursoCargo.getCategoriaId() != null) {
                    Dependencia dependencia = dependencias.get(Objects.requireNonNull(cursoCargo.getCurso()).getFacultadId() + "." + cursoCargo.getCurso().getGeograficaId());
                    Categoria categoria = cursoCargo.getCategoria();
                    cargoLiquidacions.add(new CargoLiquidacion(null,
                            legajoId,
                            anho,
                            mes,
                            dependencia.getDependenciaId(),
                            Periodo.firstDay(anho, mes),
                            Periodo.lastDay(anho, mes),
                            categoria.getCategoriaId(),
                            categoria.getNombre(),
                            categoria.getBasico(),
                            categoria.getEstadoDocente(),
                            BigDecimal.ZERO,
                            1,
                            0,
                            "A",
                            null,
                            dependencia,
                            categoria));
                }
            }
        }
        Categoria categoria = categoriaService.findByCategoriaId(980);
        for (Novedad novedad : novedadService.findAllByLegajoAndCodigo(legajoId, anho, mes, 980)) {
            cargoLiquidacions.add(new CargoLiquidacion(null,
                    legajoId,
                    anho,
                    mes,
                    novedad.getDependenciaId(),
                    Periodo.firstDay(anho, mes),
                    Periodo.lastDay(anho, mes),
                    categoria.getCategoriaId(),
                    categoria.getNombre(),
                    novedad.getImporte(),
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    1,
                    0,
                    "A",
                    null,
                    novedad.getDependencia(),
                    categoria));
        }
        cargoLiquidacions = cargoLiquidacionService.saveAll(cargoLiquidacions, mes, false);
        logCargoLiquidacions(cargoLiquidacions);
    }

    @Transactional
    public void generateCargosNoDocentes(Long legajoId, Integer anho, Integer mes) {
        List<CargoLiquidacion> cargoLiquidacions = new ArrayList<>();
        cargoLiquidacionService.deleteAllCargosNoDocentes(legajoId, anho, mes);
        for (Cargo cargo : cargoService.findAllNoDocenteByPeriodo(legajoId, anho, mes)) {
            log.debug("Cargo -> {}", cargo);
            Categoria categoria = cargo.getCategoria();
            if (categoria.getCategoriaId() != 980) {
                CargoLiquidacion cargoLiquidacion;
                cargoLiquidacions.add(cargoLiquidacion = new CargoLiquidacion(null,
                        legajoId,
                        anho,
                        mes,
                        cargo.getDependenciaId(),
                        Periodo.firstDay(anho, mes),
                        Periodo.lastDay(anho, mes),
                        categoria.getCategoriaId(),
                        categoria.getNombre(),
                        categoria.getBasico(),
                        categoria.getEstadoDocente(),
                        cargo.getHorasJornada(),
                        cargo.getJornada(),
                        cargo.getPresentismo(),
                        "A",
                        null,
                        null,
                        categoria));
                log.debug("CargoLiquidacion -> {}", cargoLiquidacion);
            }
        }
        cargoLiquidacionService.saveAll(cargoLiquidacions, mes, false);
    }

    @Transactional
    public void deleteNovedadDuplicada(Integer anho, Integer mes) {
        for (NovedadDuplicada novedadDuplicada : novedadDuplicadaService.findAllByPeriodo(anho, mes)) {
            boolean first = true;
            for (Novedad novedad : novedadService.findAllByLegajoAndCodigo(novedadDuplicada.getLegajoId(), anho, mes, novedadDuplicada.getCodigoId())) {
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
        legajoControlService.saveAll(legajos);
    }

    public String generateSIJP(Integer anho, Integer mes) {
        return null;
    }

    private BigDecimal calculateEstadoDocente(Integer categoriaId) {
        BigDecimal estadoDocente = BigDecimal.ZERO;
        if ((categoriaId > 100 && categoriaId < 201) || (categoriaId > 205 && categoriaId < 210) || (categoriaId > 214 && categoriaId < 218) || (categoriaId > 500 && categoriaId < 701) || (categoriaId > 900 && categoriaId < 980))
            estadoDocente = control.getEstadoDocenteTitular();

        if ((categoriaId > 200 && categoriaId < 206) || (categoriaId > 209 && categoriaId < 215) || (categoriaId > 216 && categoriaId < 301))
            estadoDocente = control.getEstadoDocenteAdjunto();

        if ((categoriaId > 300 && categoriaId < 501))
            estadoDocente = control.getEstadoDocenteAuxiliar();
        return estadoDocente;
    }

    private void addItem(Long legajoId, Integer anho, Integer mes, Integer codigoId, BigDecimal importe) {
        if (!items.containsKey(codigoId)) {
            items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(), BigDecimal.ZERO, null, null));
        }
        Item item = items.get(codigoId);
        item.setImporte(item.getImporte().add(importe));
    }

    private void calculateNoRemunerativos(Long legajoId, Integer anho, Integer mes, Integer codigoId, List<CodigoGrupo> codigoGrupos) {
        if (!items.containsKey(codigoId)) {
            items.put(codigoId, new Item(null, legajoId, anho, mes, codigoId, codigos.get(codigoId).getNombre(), BigDecimal.ZERO, null, null));
        }
        BigDecimal value = BigDecimal.ZERO;
        for (CodigoGrupo codigoGrupo : codigoGrupos.stream().filter(c -> c.getNoRemunerativo() == 1).toList()) {
            if (items.containsKey(codigoGrupo.getCodigoId())) {
                value = value.add(items.get(codigoGrupo.getCodigoId()).getImporte()).setScale(2, RoundingMode.HALF_UP);
            }
        }
        Item item = items.get(codigoId);
        item.setImporte(value.setScale(2, RoundingMode.HALF_UP));
    }

    private Map<Integer, BigDecimal> calculateTotalDependenciaFusionado(Long legajoId, Integer anho, Integer mes) {
        Map<Integer, BigDecimal> totalDependencia = new HashMap<>();
        for (CursoFusion cursoFusion : cursoFusionService.findAllByLegajoId(legajoId, anho, mes)) {
            this.processCategoria(totalDependencia, cursoFusion.getFacultadId(), cursoFusion.getGeograficaId(), cursoFusion.getCategoriaId(), anho, mes);
        }
        return totalDependencia;
    }

    private Map<Integer, BigDecimal> calculateTotalDependenciaSinFusion(Long legajoId, Integer anho, Integer mes) {
        Map<Integer, BigDecimal> totalDependencia = new HashMap<>();
        for (CursoCargo cursoCargo : cursoCargoService.findAllByLegajo(legajoId, anho, mes)) {
            if (cursoCargo.getCategoriaId() != null) {
                this.processCategoria(totalDependencia, cursoCargo.getCurso().getFacultadId(), cursoCargo.getCurso().getGeograficaId(), cursoCargo.getCategoriaId(), anho, mes);
            }
        }
        return totalDependencia;
    }

    private void processCategoria(Map<Integer, BigDecimal> totalDependencia, Integer facultadId, Integer geograficaId, Integer categoriaId, Integer anho, Integer mes) {
        Dependencia dependencia = dependenciaService.findFirstByFacultadIdAndGeograficaId(facultadId, geograficaId);
        if (!totalDependencia.containsKey(dependencia.getDependenciaId())) {
            totalDependencia.put(dependencia.getDependenciaId(), BigDecimal.ZERO);
        }
        try {
            CategoriaPeriodo categoriaPeriodo = categoriaPeriodoService.findByUnique(categoriaId, anho, mes);
            totalDependencia.put(dependencia.getDependenciaId(), totalDependencia.get(dependencia.getDependenciaId()).add(categoriaPeriodo.getBasico()).setScale(2, RoundingMode.HALF_UP));
        } catch (CategoriaPeriodoException e) {
            Categoria categoria = categoriaService.findByCategoriaId(categoriaId);
            totalDependencia.put(dependencia.getDependenciaId(), totalDependencia.get(dependencia.getDependenciaId()).add(categoria.getBasico()).setScale(2, RoundingMode.HALF_UP));
        }
    }

    public boolean evaluateOnlyETEC(Map<Integer, Item> items) {
        boolean onlyETEC = false;
        boolean hasETEC = false;
        boolean hasBasico = false;
        int codigoId = 1;
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
        return onlyETEC;
    }

    private void logCargo(Cargo cargo) {
        try {
            log.debug("Cargo -> {}", JsonMapper
                    .builder()
                    .findAndAddModules()
                    .build()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(cargo));
        } catch (JsonProcessingException e) {
            log.debug("Cargo jsonify error -> {}", e.getMessage());
        }
    }

    private void logCargoLiquidacions(List<CargoLiquidacion> cargoLiquidacions) {
        try {
            log.debug("CargoLiquidaciones -> {}", JsonMapper
                    .builder()
                    .findAndAddModules()
                    .build()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(cargoLiquidacions));
        } catch (JsonProcessingException e) {
            log.debug("CargoLiquidaciones jsonify error -> {}", e.getMessage());
        }
    }

}
