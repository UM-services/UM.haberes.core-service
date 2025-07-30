/**
 *
 */
package um.haberes.core.service.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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

    // region Constantes de Códigos de Liquidación
    private static final int CODIGO_BASICO = 1;
    private static final int CODIGO_ANTIGUEDAD = 2;
    private static final int CODIGO_AGUINALDO = 3;
    private static final int CODIGO_ESTADO_DOCENTE = 4;
    private static final int CODIGO_INCENTIVO_POSGRADO = 9;
    private static final int CODIGO_PRESENTISMO = 16;
    private static final int CODIGO_DESCUENTO_PRESENTISMO = 17;
    private static final int CODIGO_INASISTENCIAS = 18;
    private static final int CODIGO_HORAS_EXTRAS = 19;
    private static final int CODIGO_DESARRAIGO = 21;
    private static final int CODIGO_AGUINALDO_ETEC = 29;
    private static final int CODIGO_LICENCIA_MATERNIDAD = 30;
    private static final int CODIGO_ADICIONAL_CARGO_CLASE = 38;
    private static final int CODIGO_AJUSTE_FAVOR_EMPLEADO = 45;
    private static final int CODIGO_VACACIONES = 57;
    private static final int CODIGO_JUBILACION = 61;
    private static final int CODIGO_INSSJP = 62;
    private static final int CODIGO_OBRA_SOCIAL = 63;
    private static final int CODIGO_JUBILACION_SECUNDARIO = 70;
    private static final int CODIGO_AJUSTE_FAVOR_EMPRESA = 75;
    private static final int CODIGO_TOTAL_REMUNERATIVO = 96;
    private static final int CODIGO_TOTAL_NO_REMUNERATIVO = 97;
    private static final int CODIGO_TOTAL_DEDUCCIONES = 98;
    private static final int CODIGO_NETO = 99;
    private static final int CODIGO_NOVEDAD_AJUSTE_BASICO = 980;
    // endregion

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

    // State fields for a single liquidation run
    private Control control;
    private Persona persona;
    private List<BigDecimal> indices;
    private Map<Integer, Codigo> codigos;
    private Map<Integer, Item> items;
    private Map<Integer, Novedad> novedades;

    public MakeLiquidacionService(CargoLiquidacionService cargoLiquidacionService,
                                  DesignacionToolService designacionToolService,
                                  CargoClaseDetalleService cargoClaseDetalleService,
                                  ControlService controlService,
                                  PersonaService personaService,
                                  NovedadService novedadService,
                                  ItemService itemService,
                                  LegajoControlService legajoControlService,
                                  CodigoGrupoService codigoGrupoService,
                                  CursoDesarraigoService cursoDesarraigoService,
                                  CursoFusionService cursoFusionService,
                                  DependenciaService dependenciaService,
                                  CategoriaService categoriaService,
                                  AntiguedadService antiguedadService,
                                  LiquidacionService liquidacionService,
                                  ActividadService actividadService,
                                  LetraService letraService,
                                  CargoService cargoService,
                                  NovedadDuplicadaService novedadDuplicadaService,
                                  CursoCargoService cursoCargoService,
                                  CodigoService codigoService,
                                  AdicionalCursoTablaService adicionalCursoTablaService,
                                  CategoriaPeriodoService categoriaPeriodoService,
                                  LiquidacionAdicionalService liquidacionAdicionalService)
    {
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
        try {
            legajoControlService.findByUnique(legajoId, anho, mes);
        } catch (LegajoControlException e) {
            legajoControlService.add(new LegajoControl(null, legajoId, anho, mes, (byte) 0, (byte) 0, (byte) 0, null));
        }
        codigos = codigoService.findAll().stream().collect(Collectors.toMap(Codigo::getCodigoId, codigo -> codigo));
        novedades = novedadService.findAllByLegajo(legajoId, anho, mes).stream().filter(novedad -> !Objects.equals(novedad.getCodigoId(), CODIGO_NOVEDAD_AJUSTE_BASICO)).collect(Collectors.toMap(Novedad::getCodigoId, novedad -> novedad));
        items = new HashMap<>();
    }

    @Transactional
    public List<Item> basicoAndAntiguedad(Long legajoId, Integer anho, Integer mes) {
        makeContext(legajoId, anho, mes);
        calculaBasicoAndAntiguedad(legajoId, anho, mes);
        return new ArrayList<>(items.values());
    }

    public void liquidacionByLegajoId(Long legajoId, Integer anho, Integer mes, Boolean force) {
        if (!puedeLiquidar(legajoId, anho, mes, force)) {
            return;
        }

        inicializarLiquidacion(legajoId, anho, mes);

        List<CodigoGrupo> allCodigoGrupos = codigoGrupoService.findAll();
        List<CodigoGrupo> remunerativosGrupos = allCodigoGrupos.stream().filter(c -> c.getRemunerativo() == 1).toList();
        List<CodigoGrupo> noRemunerativosGrupos = allCodigoGrupos.stream().filter(c -> c.getNoRemunerativo() == 1).toList();
        List<CodigoGrupo> deduccionGrupos = allCodigoGrupos.stream().filter(c -> c.getDeduccion() == 1).toList();

        if (!esPeriodoLiquidable(legajoId, anho, mes)) {
            return;
        }

        BigDecimal coeficienteLiquidacion = (mes == 6 || mes == 12) ? new BigDecimal("1.5") : BigDecimal.ONE;

        // --- Start Calculation Flow ---
        calculaBasicoAndAntiguedad(legajoId, anho, mes);
        calcularConceptosGenerales(legajoId, anho, mes, remunerativosGrupos, noRemunerativosGrupos);
        calcularTotalesParciales(legajoId, anho, mes, remunerativosGrupos, noRemunerativosGrupos);
        calcularDescuentosPorAusencia(legajoId, anho, mes);
        recalcularTotales(legajoId, anho, mes, remunerativosGrupos, noRemunerativosGrupos);
        calcularAportes(legajoId, anho, mes, coeficienteLiquidacion);
        calcularDeducciones(legajoId, anho, mes, deduccionGrupos);
        aplicarAjusteNeto(legajoId, anho, mes);
        calcularNetoFinal(legajoId, anho, mes);
        // --- End Calculation Flow ---

        persistirResultadosLiquidacion(legajoId, anho, mes);
    }

    private boolean puedeLiquidar(Long legajoId, Integer anho, Integer mes, Boolean force) {
        if (!force) {
            try {
                Liquidacion liquidacion = liquidacionService.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
                if (liquidacion.getBloqueado() == (byte) 1) {
                    return false;
                }
            } catch (LiquidacionException e) {
                log.debug("No existing liquidation found, proceeding.");
            }
        }

        persona = personaService.findByLegajoId(legajoId);
        if (persona.getEstado() == 9 && "N".equals(persona.getLiquida())) {
            try {
                Liquidacion liquidacionAnterior = liquidacionService.findByPeriodoAnterior(legajoId, anho, mes);
                if (liquidacionAnterior.getEstado() == 1 && "S".equals(liquidacionAnterior.getLiquida())) {
                    log.debug("SIN Liquidacion {}/{}/{}", legajoId, anho, mes);
                    return false;
                }
            } catch (LiquidacionException e) {
                log.debug("No previous liquidation found, proceeding.");
            }
        }

        if (!(persona.getEstado() == 1 && "S".equals(persona.getLiquida()))) {
            try {
                Liquidacion liquidacionAnterior = liquidacionService.findByPeriodoAnterior(legajoId, anho, mes);
                if (liquidacionAnterior.getEstado() == 9 && "S".equals(liquidacionAnterior.getLiquida())) {
                    persona.setLiquida("N");
                    personaService.update(persona, legajoId);
                    return false;
                }
            } catch (LiquidacionException e) {
                log.debug("No previous liquidation found, proceeding.");
            }
        }
        return true;
    }

    private void inicializarLiquidacion(Long legajoId, Integer anho, Integer mes) {
        makeContext(legajoId, anho, mes);
        antiguedadService.calculate(legajoId, anho, mes);
        liquidacionAdicionalService.deleteAllByLegajo(legajoId, anho, mes);
        itemService.deleteAllByLegajo(legajoId, anho, mes);
        liquidacionService.deleteByLegajo(legajoId, anho, mes);
    }

    private boolean esPeriodoLiquidable(Long legajoId, Integer anho, Integer mes) {
        boolean tieneNovedadesEspeciales = novedades.containsKey(CODIGO_AGUINALDO) || novedades.containsKey(CODIGO_AGUINALDO_ETEC) || novedades.containsKey(CODIGO_VACACIONES);
        if (tieneNovedadesEspeciales) {
            return true;
        }
        boolean tieneCargosActivos = !cargoLiquidacionService.findAllActivosByLegajo(legajoId, anho, mes).isEmpty();
        boolean tieneCargosClase = !cargoClaseDetalleService.findAllByLegajo(legajoId, anho, mes).isEmpty();
        return tieneCargosActivos || tieneCargosClase;
    }

    private void calcularConceptosGenerales(Long legajoId, Integer anho, Integer mes, List<CodigoGrupo> remunerativos, List<CodigoGrupo> noRemunerativos) {
        // Incentivo Posgrado
        BigDecimal incentivoPosgrado = switch (persona.getPosgrado()) {
            case 1 -> control.getDoctorado();
            case 2 -> control.getMaestria();
            case 3 -> control.getEspecializacion();
            default -> BigDecimal.ZERO;
        };
        if (incentivoPosgrado.compareTo(BigDecimal.ZERO) > 0) {
            addItem(legajoId, anho, mes, CODIGO_INCENTIVO_POSGRADO, incentivoPosgrado);
        }

        // Carga de Novedades Remunerativas
        for (CodigoGrupo codigoGrupo : remunerativos) {
            if (novedades.containsKey(codigoGrupo.getCodigoId())) {
                addItem(legajoId, anho, mes, codigoGrupo.getCodigoId(), novedades.get(codigoGrupo.getCodigoId()).getImporte());
            }
        }

        // Desarraigo
        BigDecimal totalDesarraigo = cursoDesarraigoService.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes)
                .stream()
                .map(CursoDesarraigo::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalDesarraigo.compareTo(BigDecimal.ZERO) != 0) {
            addItem(legajoId, anho, mes, CODIGO_DESARRAIGO, totalDesarraigo);
        }

        // Cargos con clase
        calcularCargosConClase(legajoId, anho, mes);

        // Carga de Novedades No Remunerativas
        for (CodigoGrupo codigoGrupo : noRemunerativos) {
            if (novedades.containsKey(codigoGrupo.getCodigoId())) {
                addItem(legajoId, anho, mes, codigoGrupo.getCodigoId(), novedades.get(codigoGrupo.getCodigoId()).getImporte());
            }
        }
    }

    private void calcularCargosConClase(Long legajoId, Integer anho, Integer mes) {
        log.debug("Processing MakeLiquidacionService.calcularCargosConClase");

        for (CargoClaseDetalle detalle : cargoClaseDetalleService.findAllByLegajo(legajoId, anho, mes)) {
            log.debug("CargoClaseDetalle -> {}", detalle.jsonify());
            BigDecimal basico = detalle.getValorHora().multiply(new BigDecimal(detalle.getHoras())).setScale(2, RoundingMode.HALF_UP);
            addItem(legajoId, anho, mes, CODIGO_BASICO, basico);

            BigDecimal antiguedad = basico.multiply(indices.getFirst()).setScale(2, RoundingMode.HALF_UP);
            addItem(legajoId, anho, mes, CODIGO_ANTIGUEDAD, antiguedad);

            // Cálculo Adicional
            if (detalle.getAplicaAdicional() == 1) {
                var adicionalHora = control.getAdicionalHoraCargoClase();
                if (adicionalHora.compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal adicional = adicionalHora.multiply(new BigDecimal(detalle.getHoras())).setScale(2, RoundingMode.HALF_UP);
                    addItem(legajoId, anho, mes, CODIGO_ADICIONAL_CARGO_CLASE, adicional);
                }
            }
        }

    }

    private void calcularTotalesParciales(Long legajoId, Integer anho, Integer mes, List<CodigoGrupo> remunerativos, List<CodigoGrupo> noRemunerativos) {
        // Total Remunerativo Parcial
        BigDecimal totalRemunerativo = sumarItemsPorGrupo(remunerativos, Set.of(CODIGO_INASISTENCIAS));
        setItem(legajoId, anho, mes, CODIGO_TOTAL_REMUNERATIVO, totalRemunerativo);

        // Total No Remunerativo Parcial
        BigDecimal totalNoRemunerativo = sumarItemsPorGrupo(noRemunerativos, Collections.emptySet());
        setItem(legajoId, anho, mes, CODIGO_TOTAL_NO_REMUNERATIVO, totalNoRemunerativo);
    }

    private void calcularDescuentosPorAusencia(Long legajoId, Integer anho, Integer mes) {
        log.debug("\n\n\nProcessing MakeLiquidacionService.calcularDescuentosPorAusencia\n\n\n");
        BigDecimal aguinaldo = getItemValue(CODIGO_AGUINALDO);
        BigDecimal totalRemunerativo = getItemValue(CODIGO_TOTAL_REMUNERATIVO);
        log.debug("Total Remunerativo = {}", totalRemunerativo);
        BigDecimal valorMes = totalRemunerativo.subtract(aguinaldo).setScale(2, RoundingMode.HALF_UP);
        log.debug("Valor Mes = {}", valorMes);
        BigDecimal valorDia = valorMes.divide(new BigDecimal(30), 5, RoundingMode.HALF_UP);
        log.debug("Valor Dia = {}", valorDia);

        // Inasistencias
        if (novedades.containsKey(CODIGO_INASISTENCIAS)) {
            int diasInasistencia = novedades.get(CODIGO_INASISTENCIAS).getImporte().intValue();
            BigDecimal inasistencias = new BigDecimal(-1).multiply(new BigDecimal(diasInasistencia).multiply(valorDia)).setScale(2, RoundingMode.HALF_UP);
            log.debug("Inasistencias = {}", inasistencias);
            setItem(legajoId, anho, mes, CODIGO_INASISTENCIAS, inasistencias);
        }

        // Licencia por Maternidad
        if (novedades.containsKey(CODIGO_LICENCIA_MATERNIDAD)) {
            int diasLicencia = novedades.get(CODIGO_LICENCIA_MATERNIDAD).getImporte().intValue();
            if (persona.getEstado() == 4 && diasLicencia > 0) {
                BigDecimal licenciaMaternidad = new BigDecimal(-1).multiply(new BigDecimal(diasLicencia).multiply(valorDia)).setScale(2, RoundingMode.HALF_UP);
                addItem(legajoId, anho, mes, CODIGO_LICENCIA_MATERNIDAD, licenciaMaternidad);
            }
        }
    }

    private void recalcularTotales(Long legajoId, Integer anho, Integer mes, List<CodigoGrupo> remunerativos, List<CodigoGrupo> noRemunerativos) {
        // Recalcular Total Remunerativo
        BigDecimal totalRemunerativo = sumarItemsPorGrupo(remunerativos, Collections.emptySet());
        setItem(legajoId, anho, mes, CODIGO_TOTAL_REMUNERATIVO, totalRemunerativo);

        // Recalcular Total No Remunerativo
        BigDecimal totalNoRemunerativo = sumarItemsPorGrupo(noRemunerativos, Collections.emptySet());
        setItem(legajoId, anho, mes, CODIGO_TOTAL_NO_REMUNERATIVO, totalNoRemunerativo);
    }

    private void calcularAportes(Long legajoId, Integer anho, Integer mes, BigDecimal coeficienteLiquidacion) {
        log.debug("\n\n\nProcessing MakeLiquidacionService.calcularAportes\n\n\n");
        BigDecimal conAportes = getItemValue(CODIGO_TOTAL_REMUNERATIVO);

        // Jubilación
        BigDecimal jubilacion;
        BigDecimal coeficienteJubilacion = control.getJubilaem().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        log.debug("Calculo Jubilacion . . .");
        log.debug("Coeficiente Jubilacion = {}", coeficienteJubilacion);
        if (conAportes.compareTo(coeficienteLiquidacion.multiply(control.getMaximo1sijp())) <= 0) {
            jubilacion = conAportes.multiply(coeficienteJubilacion).setScale(2, RoundingMode.HALF_UP);
        } else {
            jubilacion = coeficienteLiquidacion.multiply(coeficienteJubilacion).multiply(control.getMaximo1sijp()).setScale(2, RoundingMode.HALF_UP);
        }
        log.debug("Jubilacion = {}", jubilacion);
        if (jubilacion.compareTo(BigDecimal.ZERO) != 0) {
            addItem(legajoId, anho, mes, CODIGO_JUBILACION, jubilacion);
        }

        // Jubilación Secundario (ETEC)
        if (evaluateOnlyETEC(items)) {
            BigDecimal jubilacionEtec = conAportes.multiply(new BigDecimal("0.02")).setScale(2, RoundingMode.HALF_UP);
            if (jubilacionEtec.compareTo(BigDecimal.ZERO) != 0) {
                addItem(legajoId, anho, mes, CODIGO_JUBILACION_SECUNDARIO, jubilacionEtec);
            }
        }

        // INSSJP
        BigDecimal inssjp;
        BigDecimal coeficienteInssjp = control.getInssjpem().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        if (conAportes.compareTo(coeficienteLiquidacion.multiply(control.getMaximo5sijp())) <= 0) {
            inssjp = conAportes.multiply(coeficienteInssjp).setScale(2, RoundingMode.HALF_UP);
        } else {
            inssjp = coeficienteLiquidacion.multiply(coeficienteInssjp).multiply(control.getMaximo5sijp()).setScale(2, RoundingMode.HALF_UP);
        }
        if (persona.getEstadoAfip() == 2) {
            inssjp = BigDecimal.ZERO;
        }
        if (inssjp.compareTo(BigDecimal.ZERO) != 0) {
            addItem(legajoId, anho, mes, CODIGO_INSSJP, inssjp);
        }

        // Obra Social
        log.debug("Calculo Obra Social . . .");
        BigDecimal obraSocial = inssjp;
        log.debug("Obra Social = {}", obraSocial);
        if (persona.getEstadoAfip() == 2 || conAportes.compareTo(BigDecimal.ZERO) == 0) {
            obraSocial = BigDecimal.ZERO;
        }
        if (obraSocial.compareTo(BigDecimal.ZERO) != 0) {
            log.debug("Registrando Obra Social . . .");
            addItem(legajoId, anho, mes, CODIGO_OBRA_SOCIAL, obraSocial);
        }
    }

    private void calcularDeducciones(Long legajoId, Integer anho, Integer mes, List<CodigoGrupo> deduccionGrupos) {
        // Cargar Novedades de Deducción
        for (CodigoGrupo codigoGrupo : deduccionGrupos) {
            if (novedades.containsKey(codigoGrupo.getCodigoId())) {
                BigDecimal value = novedades.get(codigoGrupo.getCodigoId()).getImporte();
                if (value.compareTo(BigDecimal.ZERO) != 0) {
                    addItem(legajoId, anho, mes, codigoGrupo.getCodigoId(), value);
                }
            }
        }

        // Calcular Total Deducciones
        BigDecimal totalDeducciones = sumarItemsPorGrupo(deduccionGrupos, Collections.emptySet());
        setItem(legajoId, anho, mes, CODIGO_TOTAL_DEDUCCIONES, totalDeducciones);
    }

    private void aplicarAjusteNeto(Long legajoId, Integer anho, Integer mes) {
        BigDecimal rem = getItemValue(CODIGO_TOTAL_REMUNERATIVO);
        BigDecimal noRem = getItemValue(CODIGO_TOTAL_NO_REMUNERATIVO);
        BigDecimal ded = getItemValue(CODIGO_TOTAL_DEDUCCIONES);
        BigDecimal neto = rem.add(noRem).subtract(ded);
        BigDecimal centavos = neto.subtract(neto.setScale(0, RoundingMode.HALF_UP));

        if (centavos.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = centavos.abs();
            addItem(legajoId, anho, mes, CODIGO_AJUSTE_FAVOR_EMPRESA, value);
            addItem(legajoId, anho, mes, CODIGO_TOTAL_DEDUCCIONES, value);
        }
        if (centavos.compareTo(BigDecimal.ZERO) < 0) {
            BigDecimal value = centavos.abs();
            addItem(legajoId, anho, mes, CODIGO_AJUSTE_FAVOR_EMPLEADO, value);
            addItem(legajoId, anho, mes, CODIGO_TOTAL_NO_REMUNERATIVO, value);
        }
    }

    private void calcularNetoFinal(Long legajoId, Integer anho, Integer mes) {
        BigDecimal rem = getItemValue(CODIGO_TOTAL_REMUNERATIVO);
        BigDecimal noRem = getItemValue(CODIGO_TOTAL_NO_REMUNERATIVO);
        BigDecimal ded = getItemValue(CODIGO_TOTAL_DEDUCCIONES);
        BigDecimal neto = rem.add(noRem).subtract(ded);
        setItem(legajoId, anho, mes, CODIGO_NETO, neto);
    }

    private void persistirResultadosLiquidacion(Long legajoId, Integer anho, Integer mes) {
        itemService.saveAll(new ArrayList<>(items.values()));

        Liquidacion liquidacion = new Liquidacion(null, legajoId, anho, mes, Tool.dateAbsoluteArgentina(), null,
                persona.getDependenciaId(), persona.getSalida(),
                getItemValue(CODIGO_TOTAL_REMUNERATIVO),
                getItemValue(CODIGO_TOTAL_NO_REMUNERATIVO),
                getItemValue(CODIGO_TOTAL_DEDUCCIONES),
                getItemValue(CODIGO_NETO),
                (byte) 0, persona.getEstado(), persona.getLiquida(), null, null);
        liquidacionService.add(liquidacion);

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
        LegajoControl legajoControl = legajoControlService.findByUnique(legajoId, anho, mes);
        legajoControl.setLiquidado((byte) 0);
        legajoControlService.update(legajoControl, legajoControl.getLegajoControlId());
    }

    public void calculaBasicoAndAntiguedad(Long legajoId, Integer anho, Integer mes) {
        Map<String, Dependencia> dependenciasBySede = dependenciaService.findAll().stream()
                .collect(Collectors.toMap(Dependencia::getSedeKey, Function.identity(), (d1, d2) -> d1));

        Map<Integer, Integer> horasDependencia = new HashMap<>();
        List<CursoCargo> cursosCargoConAdicional = cursoCargoService.findAllByLegajoWithAdicional(legajoId, anho, mes);
        for (CursoCargo cursoCargo : cursosCargoConAdicional) {
            Curso curso = cursoCargo.getCurso();
            if (curso == null) continue;
            String sedeKey = curso.getFacultadId() + "." + curso.getGeograficaId();
            Dependencia dependencia = dependenciasBySede.get(sedeKey);
            if (dependencia == null) continue;
            horasDependencia.compute(dependencia.getDependenciaId(), (k, v) -> (v == null ? 0 : v) + cursoCargo.getHorasSemanales().intValue());
        }

        Map<Integer, BigDecimal> totalDependencia = (control.getModoLiquidacionId() == constLiquidarConFusion)
                ? calculateTotalDependenciaFusionado(legajoId, anho, mes, dependenciasBySede)
                : calculateTotalDependenciaSinFusion(legajoId, anho, mes, dependenciasBySede);

        if (!totalDependencia.isEmpty()) {
            calcularAdicionalesPorDependencia(legajoId, anho, mes, totalDependencia, horasDependencia);
        }

        for (CargoLiquidacion cargoLiquidacion : cargoLiquidacionService.findAllByLegajo(legajoId, anho, mes)) {
            calcularConceptosPorCargo(legajoId, anho, mes, cargoLiquidacion);
        }
    }

    private void calcularAdicionalesPorDependencia(Long legajoId, Integer anho, Integer mes, Map<Integer, BigDecimal> totalDependencia, Map<Integer, Integer> horasDependencia) {
        Map<Integer, Dependencia> dependenciasById = dependenciaService.findAllByIds(totalDependencia.keySet()).stream()
                .collect(Collectors.toMap(Dependencia::getDependenciaId, Function.identity()));
        Set<Integer> facultadIds = dependenciasById.values().stream().map(Dependencia::getFacultadId).collect(Collectors.toSet());
        List<AdicionalCursoTabla> tablasAdicionales = adicionalCursoTablaService.findAllByFacultadesAndPeriodo(facultadIds, anho, mes);
        Map<Integer, AdicionalCursoTabla> tablaPorFacultad = new HashMap<>();
        Map<String, AdicionalCursoTabla> tablaPorFacultadYGeografica = new HashMap<>();
        for (AdicionalCursoTabla tabla : tablasAdicionales) {
            if (tabla.getGeograficaId() == null) {
                tablaPorFacultad.put(tabla.getFacultadId(), tabla);
            } else {
                tablaPorFacultadYGeografica.put(tabla.getFacultadId() + "." + tabla.getGeograficaId(), tabla);
            }
        }

        for (Integer dependenciaId : totalDependencia.keySet()) {
            Dependencia dependencia = dependenciasById.get(dependenciaId);
            if (dependencia == null) continue;

            AdicionalCursoTabla adicionalCursoTabla = tablaPorFacultadYGeografica.get(dependencia.getFacultadId() + "." + dependencia.getGeograficaId());
            if (adicionalCursoTabla == null) {
                adicionalCursoTabla = tablaPorFacultad.get(dependencia.getFacultadId());
            }

            if (adicionalCursoTabla != null) {
                Integer horas = horasDependencia.getOrDefault(dependenciaId, 0);
                BigDecimal totalCategoria = totalDependencia.get(dependenciaId);
                BigDecimal porcentaje = BigDecimal.ZERO;
                for (AdicionalCursoRango adicionalCursoRango : Objects.requireNonNull(adicionalCursoTabla.getAdicionalCursoRangos())) {
                    if (horas >= adicionalCursoRango.getHorasDesde() && horas <= adicionalCursoRango.getHorasHasta()) {
                        porcentaje = adicionalCursoRango.getPorcentaje();
                        break;
                    }
                }
                BigDecimal adicional = totalCategoria.multiply(porcentaje).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                if (adicional.compareTo(BigDecimal.ZERO) > 0) {
                    liquidacionAdicionalService.add(new LiquidacionAdicional(null, legajoId, anho, mes, dependenciaId, adicional, null, null));
                    addItem(legajoId, anho, mes, CODIGO_BASICO, adicional);
                    BigDecimal antiguedad = adicional.multiply(indices.getFirst()).setScale(2, RoundingMode.HALF_UP);
                    if (antiguedad.compareTo(BigDecimal.ZERO) != 0) {
                        addItem(legajoId, anho, mes, CODIGO_ANTIGUEDAD, antiguedad);
                    }
                }
            }
        }
    }

    private void calcularConceptosPorCargo(Long legajoId, Integer anho, Integer mes, CargoLiquidacion cargoLiquidacion) {
        BigDecimal valorMes = BigDecimal.ZERO;
        Categoria categoria = cargoLiquidacion.getCategoria();
        assert categoria != null;

        BigDecimal basico = cargoLiquidacion.getCategoriaBasico().multiply(new BigDecimal(cargoLiquidacion.getJornada()));
        if (cargoLiquidacion.getHorasJornada().compareTo(BigDecimal.ZERO) > 0) {
            basico = basico.multiply(cargoLiquidacion.getHorasJornada());
        }
        if (categoria.getBasico().compareTo(basico) > 0) {
            basico = categoria.getBasico();
        }
        basico = basico.setScale(2, RoundingMode.HALF_UP);
        valorMes = valorMes.add(basico);
        addItem(legajoId, anho, mes, CODIGO_BASICO, basico);

        BigDecimal estadoDocente = categoria.getEstadoDocente();
        if (estadoDocente.compareTo(BigDecimal.ZERO) != 0) {
            addItem(legajoId, anho, mes, CODIGO_ESTADO_DOCENTE, estadoDocente);
            valorMes = valorMes.add(estadoDocente);
        }

        BigDecimal antiguedad = BigDecimal.ZERO;
        if (categoria.getNoDocente() == 1) {
            antiguedad = basico.multiply(indices.get(1).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
        } else if (categoria.getDocente() == 1) {
            antiguedad = basico.multiply(indices.getFirst()).setScale(2, RoundingMode.HALF_UP);
        }
        if (antiguedad.compareTo(BigDecimal.ZERO) != 0) {
            valorMes = valorMes.add(antiguedad);
            addItem(legajoId, anho, mes, CODIGO_ANTIGUEDAD, antiguedad);
        }

        if (categoria.getNoDocente() == 1) {
            BigDecimal presentismo = basico.multiply(new BigDecimal(cargoLiquidacion.getPresentismo()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
            addItem(legajoId, anho, mes, CODIGO_PRESENTISMO, presentismo);
            valorMes = valorMes.add(presentismo);

            if (novedades.containsKey(CODIGO_PRESENTISMO) && "N".equals(novedades.get(CODIGO_PRESENTISMO).getValue())) {
                BigDecimal descuentoPresentismo = presentismo.negate();
                addItem(legajoId, anho, mes, CODIGO_DESCUENTO_PRESENTISMO, descuentoPresentismo);
            }
        }

        if (novedades.containsKey(CODIGO_HORAS_EXTRAS)) {
            int totalHoras = cargoLiquidacion.getJornada() == 1 ? 100 : 180;
            BigDecimal valorHora = valorMes.divide(new BigDecimal(totalHoras), 2, RoundingMode.HALF_UP);
            BigDecimal horasExtras = valorHora.multiply(novedades.get(CODIGO_HORAS_EXTRAS).getImporte()).setScale(2, RoundingMode.HALF_UP);
            addItem(legajoId, anho, mes, CODIGO_HORAS_EXTRAS, horasExtras);
        }
    }

    @Transactional
    public void generateCargosDocentes(Long legajoId, Integer anho, Integer mes) {
        cargoLiquidacionService.deleteAllCargosDocentes(legajoId, anho, mes);
        Map<String, Dependencia> dependencias = dependenciaService.findAll().stream().collect(Collectors.toMap(Dependencia::getSedeKey, Function.identity(), (dependencia, replacement) -> dependencia));
        List<CargoLiquidacion> cargoLiquidacions = new ArrayList<>();
        for (Cargo cargo : cargoService.findAllDocenteByPeriodo(legajoId, anho, mes)) {
            Categoria categoria = cargo.getCategoria();
            if (categoria.getCategoriaId() != CODIGO_NOVEDAD_AJUSTE_BASICO) {
                cargoLiquidacions.add(new CargoLiquidacion(null, legajoId, anho, mes, cargo.getDependenciaId(), Periodo.firstDay(anho, mes), Periodo.lastDay(anho, mes), categoria.getCategoriaId(), categoria.getNombre(), categoria.getBasico(), categoria.getEstadoDocente(), cargo.getHorasJornada(), cargo.getJornada(), cargo.getPresentismo(), "A", null, null, categoria));
            }
        }
        control = controlService.findByPeriodo(anho, mes);
        if (control.getModoLiquidacionId() == constLiquidarConFusion) {
            for (CursoFusion cursoFusion : cursoFusionService.findAllByLegajoId(legajoId, anho, mes)) {
                Dependencia dependencia = dependencias.get(cursoFusion.getFacultadId() + "." + cursoFusion.getGeograficaId());
                Categoria categoria = cursoFusion.getCategoria();
                cargoLiquidacions.add(new CargoLiquidacion(null, legajoId, anho, mes, dependencia.getDependenciaId(), Periodo.firstDay(anho, mes), Periodo.lastDay(anho, mes), categoria.getCategoriaId(), categoria.getNombre(), categoria.getBasico(), categoria.getEstadoDocente(), BigDecimal.ZERO, 1, 0, "A", null, dependencia, categoria));
            }
        }
        if (control.getModoLiquidacionId() == constLiquidarSinFusion) {
            for (CursoCargo cursoCargo : cursoCargoService.findAllByLegajo(legajoId, anho, mes)) {
                if (cursoCargo.getCategoriaId() != null) {
                    Dependencia dependencia = dependencias.get(Objects.requireNonNull(cursoCargo.getCurso()).getFacultadId() + "." + cursoCargo.getCurso().getGeograficaId());
                    Categoria categoria = cursoCargo.getCategoria();
                    cargoLiquidacions.add(new CargoLiquidacion(null, legajoId, anho, mes, dependencia.getDependenciaId(), Periodo.firstDay(anho, mes), Periodo.lastDay(anho, mes), categoria.getCategoriaId(), categoria.getNombre(), categoria.getBasico(), categoria.getEstadoDocente(), BigDecimal.ZERO, 1, 0, "A", null, dependencia, categoria));
                }
            }
        }
        Categoria categoria = categoriaService.findByCategoriaId(CODIGO_NOVEDAD_AJUSTE_BASICO);
        for (Novedad novedad : novedadService.findAllByLegajoAndCodigo(legajoId, anho, mes, CODIGO_NOVEDAD_AJUSTE_BASICO)) {
            cargoLiquidacions.add(new CargoLiquidacion(null, legajoId, anho, mes, novedad.getDependenciaId(), Periodo.firstDay(anho, mes), Periodo.lastDay(anho, mes), categoria.getCategoriaId(), categoria.getNombre(), novedad.getImporte(), BigDecimal.ZERO, BigDecimal.ZERO, 1, 0, "A", null, novedad.getDependencia(), categoria));
        }
        cargoLiquidacions = cargoLiquidacionService.saveAll(cargoLiquidacions, mes, false);
    }

    @Transactional
    public void generateCargosNoDocentes(Long legajoId, Integer anho, Integer mes) {
        List<CargoLiquidacion> cargoLiquidacions = new ArrayList<>();
        cargoLiquidacionService.deleteAllCargosNoDocentes(legajoId, anho, mes);
        for (Cargo cargo : cargoService.findAllNoDocenteByPeriodo(legajoId, anho, mes)) {
            log.debug("Cargo -> {}", cargo);
            Categoria categoria = cargo.getCategoria();
            if (categoria.getCategoriaId() != CODIGO_NOVEDAD_AJUSTE_BASICO) {
                cargoLiquidacions.add(new CargoLiquidacion(null, legajoId, anho, mes, cargo.getDependenciaId(), Periodo.firstDay(anho, mes), Periodo.lastDay(anho, mes), categoria.getCategoriaId(), categoria.getNombre(), categoria.getBasico(), categoria.getEstadoDocente(), cargo.getHorasJornada(), cargo.getJornada(), cargo.getPresentismo(), "A", null, null, categoria));
            }
        }
        cargoLiquidacionService.saveAll(cargoLiquidacions, mes, false);
    }

    @Transactional
    public void deleteNovedadDuplicada(Integer anho, Integer mes) {
        for (NovedadDuplicada novedadDuplicada : novedadDuplicadaService.findAllByPeriodo(anho, mes)) {
            novedadService.findAllByLegajoAndCodigo(novedadDuplicada.getLegajoId(), anho, mes, novedadDuplicada.getCodigoId())
                    .stream().skip(1).forEach(novedad -> novedadService.deleteByNovedadId(novedad.getNovedadId()));
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

    private void addItem(Long legajoId, Integer anho, Integer mes, Integer codigoId, BigDecimal importe) {
        log.debug("Processing MakeLiquidacionService.addItem");
        Item item = items.computeIfAbsent(codigoId, k -> new Item(null, legajoId, anho, mes, k, codigos.get(k).getNombre(), BigDecimal.ZERO, null, null));
        item.setImporte(item.getImporte().add(importe).setScale(2, RoundingMode.HALF_UP));
        log.debug("Item -> {}", item.jsonify());
    }

    private void setItem(Long legajoId, Integer anho, Integer mes, Integer codigoId, BigDecimal importe) {
        log.debug("Processing MakeLiquidacionService.setItem");
        Item item = items.computeIfAbsent(codigoId, k -> new Item(null, legajoId, anho, mes, k, codigos.get(k).getNombre(), BigDecimal.ZERO, null, null));
        item.setImporte(importe.setScale(2, RoundingMode.HALF_UP));
        log.debug("Item -> {}", item.jsonify());
    }

    private BigDecimal getItemValue(Integer codigoId) {
        return Optional.ofNullable(items.get(codigoId)).map(Item::getImporte).orElse(BigDecimal.ZERO);
    }

    private BigDecimal sumarItemsPorGrupo(List<CodigoGrupo> grupo, Set<Integer> codigosExcluidos) {
        return grupo.stream()
                .map(CodigoGrupo::getCodigoId)
                .filter(id -> !codigosExcluidos.contains(id))
                .map(this::getItemValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<Integer, BigDecimal> calculateTotalDependenciaFusionado(Long legajoId, Integer anho, Integer mes, Map<String, Dependencia> dependenciasBySede) {
        Map<Integer, BigDecimal> totalDependencia = new HashMap<>();
        List<CursoFusion> cursoFusions = cursoFusionService.findAllByLegajoId(legajoId, anho, mes);
        if (cursoFusions.isEmpty()) return totalDependencia;

        Set<Integer> categoriaIds = cursoFusions.stream().map(CursoFusion::getCategoriaId).collect(Collectors.toSet());
        Map<Integer, CategoriaPeriodo> categoriaPeriodos = categoriaPeriodoService.findAllByCategoriaIdsAndPeriodo(categoriaIds, anho, mes).stream().collect(Collectors.toMap(CategoriaPeriodo::getCategoriaId, Function.identity()));
        Map<Integer, Categoria> categorias = categoriaService.findAllByIds(categoriaIds).stream().collect(Collectors.toMap(Categoria::getCategoriaId, Function.identity()));

        for (CursoFusion cursoFusion : cursoFusions) {
            Dependencia dependencia = dependenciasBySede.get(cursoFusion.getFacultadId() + "." + cursoFusion.getGeograficaId());
            if (dependencia == null) continue;
            BigDecimal basico = Optional.ofNullable(categoriaPeriodos.get(cursoFusion.getCategoriaId())).map(CategoriaPeriodo::getBasico)
                    .orElseGet(() -> Optional.ofNullable(categorias.get(cursoFusion.getCategoriaId())).map(Categoria::getBasico).orElse(BigDecimal.ZERO));
            totalDependencia.compute(dependencia.getDependenciaId(), (k, v) -> (v == null) ? basico : v.add(basico));
        }
        totalDependencia.replaceAll((k, v) -> v.setScale(2, RoundingMode.HALF_UP));
        return totalDependencia;
    }

    private Map<Integer, BigDecimal> calculateTotalDependenciaSinFusion(Long legajoId, Integer anho, Integer mes, Map<String, Dependencia> dependenciasBySede) {
        Map<Integer, BigDecimal> totalDependencia = new HashMap<>();
        List<CursoCargo> cursoCargos = cursoCargoService.findAllByLegajo(legajoId, anho, mes);
        if (cursoCargos.isEmpty()) return totalDependencia;

        Set<Integer> categoriaIds = cursoCargos.stream().map(CursoCargo::getCategoriaId).filter(Objects::nonNull).collect(Collectors.toSet());
        if (categoriaIds.isEmpty()) return totalDependencia;

        Map<Integer, CategoriaPeriodo> categoriaPeriodos = categoriaPeriodoService.findAllByCategoriaIdsAndPeriodo(categoriaIds, anho, mes).stream().collect(Collectors.toMap(CategoriaPeriodo::getCategoriaId, Function.identity()));
        Map<Integer, Categoria> categorias = categoriaService.findAllByIds(categoriaIds).stream().collect(Collectors.toMap(Categoria::getCategoriaId, Function.identity()));

        for (CursoCargo cursoCargo : cursoCargos) {
            if (cursoCargo.getCategoriaId() != null) {
                Curso curso = cursoCargo.getCurso();
                if (curso == null) continue;
                Dependencia dependencia = dependenciasBySede.get(curso.getFacultadId() + "." + curso.getGeograficaId());
                if (dependencia == null) continue;
                BigDecimal basico = Optional.ofNullable(categoriaPeriodos.get(cursoCargo.getCategoriaId())).map(CategoriaPeriodo::getBasico)
                        .orElseGet(() -> Optional.ofNullable(categorias.get(cursoCargo.getCategoriaId())).map(Categoria::getBasico).orElse(BigDecimal.ZERO));
                totalDependencia.compute(dependencia.getDependenciaId(), (k, v) -> (v == null) ? basico : v.add(basico));
            }
        }
        totalDependencia.replaceAll((k, v) -> v.setScale(2, RoundingMode.HALF_UP));
        return totalDependencia;
    }

    public boolean evaluateOnlyETEC(Map<Integer, Item> items) {
        boolean hasBasico = items.containsKey(CODIGO_BASICO) && items.get(CODIGO_BASICO).getImporte().compareTo(BigDecimal.ZERO) != 0;
        boolean hasETEC = items.containsKey(CODIGO_AGUINALDO_ETEC) && items.get(CODIGO_AGUINALDO_ETEC).getImporte().compareTo(BigDecimal.ZERO) != 0;
        return hasETEC && !hasBasico;
    }

}
