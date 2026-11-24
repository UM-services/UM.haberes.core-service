package um.haberes.core.service.facade;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import um.haberes.core.exception.*;
import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso_cargo.application.service.CursoCargoService;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.application.service.CursoDesarraigoService;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;
import um.haberes.core.hexagonal.cursos.curso_fusion.application.service.CursoFusionService;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.etec.arraigo_escala.application.service.EscalaArraigoEtecService;
import um.haberes.core.hexagonal.etec.control_etec.application.service.ControlEtecService;
import um.haberes.core.hexagonal.etec.hora_escala.application.service.EscalaHoraEtecService;
import um.haberes.core.hexagonal.etec.hora_escala.domain.model.AgrupadorHorasEtec;
import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;
import um.haberes.core.hexagonal.etec.liquidacion.application.service.EtecLiquidacionCalculator;
import um.haberes.core.hexagonal.etec.presentismo.application.service.EtecPresentismoService;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.service.EtecPresentismoHorasService;
import um.haberes.core.hexagonal.liquidaciones.cargo.application.service.CargoService;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.UniversoCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service.CatalogoUniversosCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GenerarCargosLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service.CargoLiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.categoria.application.service.CategoriaService;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.codigo.application.service.CodigoService;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.item.application.service.ItemService;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.letra.application.exception.LetraException;
import um.haberes.core.hexagonal.liquidaciones.letra.application.service.LetraService;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.exception.LiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.service.LiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.service.NovedadService;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.sijp.application.exception.SijpException;
import um.haberes.core.hexagonal.liquidaciones.sijp.application.service.SijpService;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.ArchivoSijp;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.VarianteSijp;
import um.haberes.core.hexagonal.personas.dependencia.application.service.DependenciaService;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.persona.application.service.PersonaService;
import um.haberes.core.model.*;
import um.haberes.core.model.view.NovedadDuplicada;
import um.haberes.core.service.*;
import um.haberes.core.service.facade.liquidaciones.LiquidacionState;
import um.haberes.core.service.view.NovedadDuplicadaService;
import um.haberes.core.util.Periodo;
import um.haberes.core.util.Tool;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author daniel
 */
@Service
@Slf4j
@RequiredArgsConstructor
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
    private final GenerarCargosLegajoUseCase generarCargosLegajoUseCase;
    private final ControlEtecService controlEtecService;
    private final EscalaArraigoEtecService escalaArraigoEtecService;
    private final EscalaHoraEtecService escalaHoraEtecService;
    private final EtecLiquidacionCalculator etecLiquidacionCalculator;
    private final EtecPresentismoService etecPresentismoService;
    private final EtecPresentismoHorasService etecPresentismoHorasService;
    private final SijpService sijpService;
    private final Environment environment;

    @Transactional
    public LiquidacionState makeContext(Long legajoId, Integer anho, Integer mes) {
        this.deleteNovedadDuplicada(anho, mes);
        LiquidacionState state = new LiquidacionState();
        state.setIndices(designacionToolService.indiceAntiguedad(legajoId, anho, mes));
        state.setPersona(personaService.findByLegajoId(legajoId));
        state.setControl(controlService.findByPeriodo(anho, mes));
        try {
            legajoControlService.findByUnique(legajoId, anho, mes);
        } catch (LegajoControlException e) {
            legajoControlService.add(new LegajoControlEntity(null, legajoId, anho, mes, (byte) 0, (byte) 0, (byte) 0, null));
        }
        state.setCodigos(codigoService.findAll().stream().collect(Collectors.toMap(Codigo::getCodigoId, codigo -> codigo)));
        state.setControlEtec(controlEtecService.findByPeriodo(anho, mes).orElse(null));
        Set<Integer> etecCategoriasSinPresentismo = new HashSet<>(
                etecPresentismoService.categoriasSinPresentismo(legajoId, anho, mes));
        if (etecPresentismoHorasService.existe(legajoId, anho, mes)) {
            escalaHoraEtecService.findByPeriodo(anho, mes).stream()
                    .map(EscalaHoraEtec::getCategoriaId)
                    .filter(Objects::nonNull)
                    .forEach(etecCategoriasSinPresentismo::add);
        }
        state.setEtecCategoriasSinPresentismo(etecCategoriasSinPresentismo);
        state.setNovedades(novedadService.findAllByLegajo(legajoId, anho, mes).stream().filter(novedad -> !Objects.equals(novedad.getCodigoId(), CODIGO_NOVEDAD_AJUSTE_BASICO)).collect(Collectors.toMap(Novedad::getCodigoId, novedad -> novedad)));
        return state;
    }

    @Transactional
    public List<Item> basicoAndAntiguedad(Long legajoId, Integer anho, Integer mes) {
        LiquidacionState state = makeContext(legajoId, anho, mes);
        calculaBasicoAndAntiguedad(legajoId, anho, mes, state);
        return new ArrayList<>(state.getItems().values());
    }

    public void liquidacionByLegajoId(Long legajoId, Integer anho, Integer mes, Boolean force) {
        LiquidacionState state = new LiquidacionState();
        if (!puedeLiquidar(legajoId, anho, mes, force, state)) {
            return;
        }

        inicializarLiquidacion(legajoId, anho, mes, state);

        List<CodigoGrupoEntity> allCodigoGrupos = codigoGrupoService.findAll();
        List<CodigoGrupoEntity> remunerativosGrupos = allCodigoGrupos.stream().filter(c -> c.getRemunerativo() == 1).toList();
        List<CodigoGrupoEntity> noRemunerativosGrupos = allCodigoGrupos.stream().filter(c -> c.getNoRemunerativo() == 1).toList();
        List<CodigoGrupoEntity> deduccionGrupos = allCodigoGrupos.stream().filter(c -> c.getDeduccion() == 1).toList();

        if (!esPeriodoLiquidable(legajoId, anho, mes, state)) {
            return;
        }

        BigDecimal coeficienteLiquidacion = (mes == 6 || mes == 12) ? new BigDecimal("1.5") : BigDecimal.ONE;

        // --- Start Calculation Flow ---
        calculaBasicoAndAntiguedad(legajoId, anho, mes, state);
        calcularConceptosEtec(legajoId, anho, mes, state);
        calcularConceptosGenerales(legajoId, anho, mes, remunerativosGrupos, noRemunerativosGrupos, state);
        calcularTotalesParciales(legajoId, anho, mes, remunerativosGrupos, noRemunerativosGrupos, state);
        calcularDescuentosPorAusencia(legajoId, anho, mes, state);
        recalcularTotales(legajoId, anho, mes, remunerativosGrupos, noRemunerativosGrupos, state);
        calcularAportes(legajoId, anho, mes, coeficienteLiquidacion, state);
        calcularDeducciones(legajoId, anho, mes, deduccionGrupos, state);
        aplicarAjusteNeto(legajoId, anho, mes, state);
        calcularNetoFinal(legajoId, anho, mes, state);
        // --- End Calculation Flow ---

        persistirResultadosLiquidacion(legajoId, anho, mes, state);
    }

    private boolean puedeLiquidar(Long legajoId, Integer anho, Integer mes, Boolean force, LiquidacionState state) {
        if (!force) {
            try {
                Liquidacion liquidacion = liquidacionService.getLiquidacionByUniqueKey(legajoId, anho, mes);
                if (liquidacion.getBloqueado() == (byte) 1) {
                    return false;
                }
            } catch (LiquidacionException e) {
                log.debug("No existing liquidation found, proceeding.");
            }
        }

        state.setPersona(personaService.findByLegajoId(legajoId));
        if (state.getPersona().getEstado() == 9 && "N".equals(state.getPersona().getLiquida())) {
            try {
                Liquidacion liquidacionAnterior = liquidacionService.getLiquidacionByPeriodoAnterior(legajoId, anho, mes);
                if (liquidacionAnterior.getEstado() == 1 && "S".equals(liquidacionAnterior.getLiquida())) {
                    log.debug("SIN LiquidacionEntity {}/{}/{}", legajoId, anho, mes);
                    return false;
                }
            } catch (LiquidacionException e) {
                log.debug("No previous liquidation found, proceeding.");
            }
        }

        if (!(state.getPersona().getEstado() == 1 && "S".equals(state.getPersona().getLiquida()))) {
            try {
                Liquidacion liquidacionAnterior = liquidacionService.getLiquidacionByPeriodoAnterior(legajoId, anho, mes);
                if (liquidacionAnterior.getEstado() == 9 && "S".equals(liquidacionAnterior.getLiquida())) {
                    state.getPersona().setLiquida("N");
                    personaService.update(state.getPersona(), legajoId);
                    return false;
                }
            } catch (LiquidacionException e) {
                log.debug("No previous liquidation found, proceeding.");
            }
        }
        return true;
    }

    private void inicializarLiquidacion(Long legajoId, Integer anho, Integer mes, LiquidacionState state) {
        LiquidacionState freshState = makeContext(legajoId, anho, mes);
        state.copiarContexto(freshState);

        antiguedadService.calculate(legajoId, anho, mes);
        cargarPorcentajeArraigo(legajoId, anho, mes, state);
        liquidacionAdicionalService.deleteAllByLegajo(legajoId, anho, mes);
        itemService.deleteAllByLegajo(legajoId, anho, mes);
        liquidacionService.deleteByLegajo(legajoId, anho, mes);
    }

    /**
     * Resuelve el porcentaje de arraigo ETEC según la antigüedad docente del legajo. Se ejecuta
     * después de {@code antiguedadService.calculate}, así que el registro del período está al día.
     * Sin antigüedad docente, o con menos de dos años, el porcentaje queda en cero.
     */
    private void cargarPorcentajeArraigo(Long legajoId, Integer anho, Integer mes, LiquidacionState state) {
        int anios = 0;
        try {
            anios = Math.max(0, antiguedadService.findByUnique(legajoId, anho, mes).getMesesDocentes() / 12);
        } catch (AntiguedadException e) {
            log.debug("Sin antigüedad para el legajo {} en {}/{}: {}", legajoId, anho, mes, e.getMessage());
        }
        state.setPorcentajeArraigoEtec(escalaArraigoEtecService.porcentajeParaAnios(anios));
    }

    private boolean esPeriodoLiquidable(Long legajoId, Integer anho, Integer mes, LiquidacionState state) {
        boolean tieneNovedadesEspeciales = state.getNovedades().containsKey(CODIGO_AGUINALDO) || state.getNovedades().containsKey(CODIGO_AGUINALDO_ETEC) || state.getNovedades().containsKey(CODIGO_VACACIONES);
        if (tieneNovedadesEspeciales) {
            return true;
        }
        boolean tieneCargosActivos = !cargoLiquidacionService.findAllActivosByLegajo(legajoId, anho, mes).isEmpty();
        boolean tieneCargosClase = !cargoClaseDetalleService.findAllByLegajo(legajoId, anho, mes).isEmpty();
        return tieneCargosActivos || tieneCargosClase;
    }

    private void calcularConceptosGenerales(Long legajoId, Integer anho, Integer mes, List<CodigoGrupoEntity> remunerativos, List<CodigoGrupoEntity> noRemunerativos, LiquidacionState state) {
        // Incentivo Posgrado
        BigDecimal incentivoPosgrado = switch (state.getPersona().getPosgrado()) {
            case 1 -> state.getControl().getDoctorado();
            case 2 -> state.getControl().getMaestria();
            case 3 -> state.getControl().getEspecializacion();
            default -> BigDecimal.ZERO;
        };
        if (incentivoPosgrado.compareTo(BigDecimal.ZERO) > 0) {
            addItem(legajoId, anho, mes, CODIGO_INCENTIVO_POSGRADO, incentivoPosgrado, state);
        }

        // Carga de Novedades Remunerativas
        for (CodigoGrupoEntity codigoGrupo : remunerativos) {
            if (state.getNovedades().containsKey(codigoGrupo.getCodigoId())) {
                addItem(legajoId, anho, mes, codigoGrupo.getCodigoId(), state.getNovedades().get(codigoGrupo.getCodigoId()).getImporte(), state);
            }
        }

        // Desarraigo
        BigDecimal totalDesarraigo = cursoDesarraigoService.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes)
                .stream()
                .map(CursoDesarraigo::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalDesarraigo.compareTo(BigDecimal.ZERO) != 0) {
            addItem(legajoId, anho, mes, CODIGO_DESARRAIGO, totalDesarraigo, state);
        }

        // Cargos con clase
        calcularCargosConClase(legajoId, anho, mes, state);

        // Carga de Novedades No Remunerativas
        for (CodigoGrupoEntity codigoGrupo : noRemunerativos) {
            if (state.getNovedades().containsKey(codigoGrupo.getCodigoId())) {
                addItem(legajoId, anho, mes, codigoGrupo.getCodigoId(), state.getNovedades().get(codigoGrupo.getCodigoId()).getImporte(), state);
            }
        }
    }

    private void calcularCargosConClase(Long legajoId, Integer anho, Integer mes, LiquidacionState state) {
        log.debug("Processing MakeLiquidacionService.calcularCargosConClase");

        state.setCargoClases(cargoClaseDetalleService.findAllByLegajo(legajoId, anho, mes));

        for (CargoClaseDetalleEntity detalle : state.getCargoClases()) {
            log.debug("CargoClaseDetalleEntity -> {}", detalle.jsonify());
            BigDecimal basico = detalle.getValorHora().multiply(new BigDecimal(detalle.getHoras())).setScale(2, RoundingMode.HALF_UP);
            addItem(legajoId, anho, mes, CODIGO_BASICO, basico, state);

            BigDecimal antiguedad = basico.multiply(state.getIndices().getFirst()).setScale(2, RoundingMode.HALF_UP);
            addItem(legajoId, anho, mes, CODIGO_ANTIGUEDAD, antiguedad, state);

            // Cálculo Adicional
            if (detalle.getAplicaAdicional() == 1) {
                var adicionalHora = state.getControl().getAdicionalHoraCargoClase();
                if (adicionalHora.compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal adicional = adicionalHora.multiply(new BigDecimal(detalle.getHoras())).setScale(2, RoundingMode.HALF_UP);
                    addItem(legajoId, anho, mes, CODIGO_ADICIONAL_CARGO_CLASE, adicional, state);
                }
            }
        }

    }

    private void calcularTotalesParciales(Long legajoId, Integer anho, Integer mes, List<CodigoGrupoEntity> remunerativos, List<CodigoGrupoEntity> noRemunerativos, LiquidacionState state) {
        // Total Remunerativo Parcial
        BigDecimal totalRemunerativo = sumarItemsPorGrupo(remunerativos, Set.of(CODIGO_INASISTENCIAS), state);
        setItem(legajoId, anho, mes, CODIGO_TOTAL_REMUNERATIVO, totalRemunerativo, state);

        // Total No Remunerativo Parcial
        BigDecimal totalNoRemunerativo = sumarItemsPorGrupo(noRemunerativos, Collections.emptySet(), state);
        setItem(legajoId, anho, mes, CODIGO_TOTAL_NO_REMUNERATIVO, totalNoRemunerativo, state);
    }

    private void calcularDescuentosPorAusencia(Long legajoId, Integer anho, Integer mes, LiquidacionState state) {
        log.debug("\n\n\nProcessing MakeLiquidacionService.calcularDescuentosPorAusencia\n\n\n");
        BigDecimal aguinaldo = getItemValue(CODIGO_AGUINALDO, state);
        BigDecimal totalRemunerativo = getItemValue(CODIGO_TOTAL_REMUNERATIVO, state);
        log.debug("Total Remunerativo = {}", totalRemunerativo);
        BigDecimal valorMes = totalRemunerativo.subtract(aguinaldo).setScale(2, RoundingMode.HALF_UP);
        log.debug("Valor Mes = {}", valorMes);
        BigDecimal valorDia = valorMes.divide(new BigDecimal(30), 5, RoundingMode.HALF_UP);
        log.debug("Valor Dia = {}", valorDia);

        // Inasistencias
        if (state.getNovedades().containsKey(CODIGO_INASISTENCIAS)) {
            int diasInasistencia = state.getNovedades().get(CODIGO_INASISTENCIAS).getImporte().intValue();
            BigDecimal inasistencias = new BigDecimal(-1).multiply(new BigDecimal(diasInasistencia).multiply(valorDia)).setScale(2, RoundingMode.HALF_UP);
            log.debug("Inasistencias = {}", inasistencias);
            setItem(legajoId, anho, mes, CODIGO_INASISTENCIAS, inasistencias, state);
        }

        // Licencia por Maternidad
        if (state.getNovedades().containsKey(CODIGO_LICENCIA_MATERNIDAD)) {
            int diasLicencia = state.getNovedades().get(CODIGO_LICENCIA_MATERNIDAD).getImporte().intValue();
            if (state.getPersona().getEstado() == 4 && diasLicencia > 0) {
                BigDecimal licenciaMaternidad = new BigDecimal(-1).multiply(new BigDecimal(diasLicencia).multiply(valorDia)).setScale(2, RoundingMode.HALF_UP);
                addItem(legajoId, anho, mes, CODIGO_LICENCIA_MATERNIDAD, licenciaMaternidad, state);
            }
        }
    }

    private void recalcularTotales(Long legajoId, Integer anho, Integer mes, List<CodigoGrupoEntity> remunerativos, List<CodigoGrupoEntity> noRemunerativos, LiquidacionState state) {
        // Recalcular Total Remunerativo
        BigDecimal totalRemunerativo = sumarItemsPorGrupo(remunerativos, Collections.emptySet(), state);
        setItem(legajoId, anho, mes, CODIGO_TOTAL_REMUNERATIVO, totalRemunerativo, state);

        // Recalcular Total No Remunerativo
        BigDecimal totalNoRemunerativo = sumarItemsPorGrupo(noRemunerativos, Collections.emptySet(), state);
        setItem(legajoId, anho, mes, CODIGO_TOTAL_NO_REMUNERATIVO, totalNoRemunerativo, state);
    }

    private void calcularAportes(Long legajoId, Integer anho, Integer mes, BigDecimal coeficienteLiquidacion, LiquidacionState state) {
        log.debug("\n\n\nProcessing MakeLiquidacionService.calcularAportes\n\n\n");
        BigDecimal conAportes = getItemValue(CODIGO_TOTAL_REMUNERATIVO, state);

        // Jubilación
        BigDecimal jubilacion;
        BigDecimal coeficienteJubilacion = state.getControl().getJubilaem().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        log.debug("Calculo Jubilacion . . .");
        log.debug("Coeficiente Jubilacion = {}", coeficienteJubilacion);
        if (conAportes.compareTo(coeficienteLiquidacion.multiply(state.getControl().getMaximo1sijp())) <= 0) {
            jubilacion = conAportes.multiply(coeficienteJubilacion).setScale(2, RoundingMode.HALF_UP);
        } else {
            jubilacion = coeficienteLiquidacion.multiply(coeficienteJubilacion).multiply(state.getControl().getMaximo1sijp()).setScale(2, RoundingMode.HALF_UP);
        }
        log.debug("Jubilacion = {}", jubilacion);
        if (jubilacion.compareTo(BigDecimal.ZERO) != 0) {
            addItem(legajoId, anho, mes, CODIGO_JUBILACION, jubilacion, state);
        }

        // Jubilación Secundario (ETEC)
        if (evaluarJubilacionSecundaria(legajoId, anho, mes, state)) {
            BigDecimal jubilacionEtec = conAportes.multiply(new BigDecimal("0.02")).setScale(2, RoundingMode.HALF_UP);
            if (jubilacionEtec.compareTo(BigDecimal.ZERO) != 0) {
                addItem(legajoId, anho, mes, CODIGO_JUBILACION_SECUNDARIO, jubilacionEtec, state);
            }
        }

        // INSSJP
        BigDecimal inssjp;
        BigDecimal coeficienteInssjp = state.getControl().getInssjpem().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        if (conAportes.compareTo(coeficienteLiquidacion.multiply(state.getControl().getMaximo5sijp())) <= 0) {
            inssjp = conAportes.multiply(coeficienteInssjp).setScale(2, RoundingMode.HALF_UP);
        } else {
            inssjp = coeficienteLiquidacion.multiply(coeficienteInssjp).multiply(state.getControl().getMaximo5sijp()).setScale(2, RoundingMode.HALF_UP);
        }
        if (state.getPersona().getEstadoAfip() == 2) {
            inssjp = BigDecimal.ZERO;
        }
        if (inssjp.compareTo(BigDecimal.ZERO) != 0) {
            addItem(legajoId, anho, mes, CODIGO_INSSJP, inssjp, state);
        }

        // Obra Social
        log.debug("Calculo Obra Social . . .");
        BigDecimal obraSocial = inssjp;
        log.debug("Obra Social = {}", obraSocial);
        if (state.getPersona().getEstadoAfip() == 2 || conAportes.compareTo(BigDecimal.ZERO) == 0) {
            obraSocial = BigDecimal.ZERO;
        }
        if (obraSocial.compareTo(BigDecimal.ZERO) != 0) {
            log.debug("Registrando Obra Social . . .");
            addItem(legajoId, anho, mes, CODIGO_OBRA_SOCIAL, obraSocial, state);
        }
    }

    private void calcularDeducciones(Long legajoId, Integer anho, Integer mes, List<CodigoGrupoEntity> deduccionGrupos, LiquidacionState state) {
        // Cargar Novedades de Deducción
        for (CodigoGrupoEntity codigoGrupo : deduccionGrupos) {
            if (state.getNovedades().containsKey(codigoGrupo.getCodigoId())) {
                BigDecimal value = state.getNovedades().get(codigoGrupo.getCodigoId()).getImporte();
                if (value.compareTo(BigDecimal.ZERO) != 0) {
                    addItem(legajoId, anho, mes, codigoGrupo.getCodigoId(), value, state);
                }
            }
        }

        // Calcular Total Deducciones
        BigDecimal totalDeducciones = sumarItemsPorGrupo(deduccionGrupos, Collections.emptySet(), state);
        setItem(legajoId, anho, mes, CODIGO_TOTAL_DEDUCCIONES, totalDeducciones, state);
    }

    private void aplicarAjusteNeto(Long legajoId, Integer anho, Integer mes, LiquidacionState state) {
        BigDecimal rem = getItemValue(CODIGO_TOTAL_REMUNERATIVO, state);
        BigDecimal noRem = getItemValue(CODIGO_TOTAL_NO_REMUNERATIVO, state);
        BigDecimal ded = getItemValue(CODIGO_TOTAL_DEDUCCIONES, state);
        BigDecimal neto = rem.add(noRem).subtract(ded);
        BigDecimal centavos = neto.subtract(neto.setScale(0, RoundingMode.HALF_UP));

        if (centavos.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = centavos.abs();
            addItem(legajoId, anho, mes, CODIGO_AJUSTE_FAVOR_EMPRESA, value, state);
            addItem(legajoId, anho, mes, CODIGO_TOTAL_DEDUCCIONES, value, state);
        }
        if (centavos.compareTo(BigDecimal.ZERO) < 0) {
            BigDecimal value = centavos.abs();
            addItem(legajoId, anho, mes, CODIGO_AJUSTE_FAVOR_EMPLEADO, value, state);
            addItem(legajoId, anho, mes, CODIGO_TOTAL_NO_REMUNERATIVO, value, state);
        }
    }

    private void calcularNetoFinal(Long legajoId, Integer anho, Integer mes, LiquidacionState state) {
        BigDecimal rem = getItemValue(CODIGO_TOTAL_REMUNERATIVO, state);
        BigDecimal noRem = getItemValue(CODIGO_TOTAL_NO_REMUNERATIVO, state);
        BigDecimal ded = getItemValue(CODIGO_TOTAL_DEDUCCIONES, state);
        BigDecimal neto = rem.add(noRem).subtract(ded);
        setItem(legajoId, anho, mes, CODIGO_NETO, neto, state);
    }

    private void persistirResultadosLiquidacion(Long legajoId, Integer anho, Integer mes, LiquidacionState state) {
        itemService.saveAllItems(new ArrayList<>(state.getItems().values()));

        Liquidacion liquidacion = Liquidacion.builder()
                .legajoId(legajoId)
                .anho(anho)
                .mes(mes)
                .fechaLiquidacion(Tool.dateAbsoluteArgentina())
                .dependenciaId(state.getPersona().getDependenciaId())
                .salida(state.getPersona().getSalida())
                .totalRemunerativo(getItemValue(CODIGO_TOTAL_REMUNERATIVO, state))
                .totalNoRemunerativo(getItemValue(CODIGO_TOTAL_NO_REMUNERATIVO, state))
                .totalDeduccion(getItemValue(CODIGO_TOTAL_DEDUCCIONES, state))
                .totalNeto(getItemValue(CODIGO_NETO, state))
                .bloqueado((byte) 0)
                .estado(state.getPersona().getEstado())
                .liquida(state.getPersona().getLiquida())
                .build();
        liquidacionService.add(liquidacion);

        try {
            actividadService.findByUnique(legajoId, anho, mes);
        } catch (ActividadException e) {
            actividadService.add(new ActividadEntity(null, legajoId, anho, mes, (byte) 1, (byte) 1, (byte) 1, 10, null, null));
        }

        try {
            letraService.findByUnique(legajoId, anho, mes);
        } catch (LetraException e) {
            letraService.add(new Letra(null, legajoId, anho, mes, BigDecimal.ZERO, ""));
        }

        LegajoControlEntity legajoControl = legajoControlService.findByUnique(legajoId, anho, mes);
        legajoControl.setLiquidado((byte) 1);
        legajoControlService.update(legajoControl, legajoControl.getLegajoControlId());
    }

    @Transactional
    public void deleteLiquidacionByLegajoId(Long legajoId, Integer anho, Integer mes, Boolean force) {
        if (!force) {
            try {
                Liquidacion liquidacion = liquidacionService.getLiquidacionByUniqueKey(legajoId, anho, mes);
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
        LegajoControlEntity legajoControl = legajoControlService.findByUnique(legajoId, anho, mes);
        legajoControl.setLiquidado((byte) 0);
        legajoControlService.update(legajoControl, legajoControl.getLegajoControlId());
    }

    public void calculaBasicoAndAntiguedad(Long legajoId, Integer anho, Integer mes, LiquidacionState state) {
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

        Map<Integer, BigDecimal> totalDependencia = (state.getControl().getModoLiquidacionId() == constLiquidarConFusion)
                ? calculateTotalDependenciaFusionado(legajoId, anho, mes, dependenciasBySede)
                : calculateTotalDependenciaSinFusion(legajoId, anho, mes, dependenciasBySede);

        if (!totalDependencia.isEmpty()) {
            calcularAdicionalesPorDependencia(legajoId, anho, mes, totalDependencia, horasDependencia, state);
        }

        for (CargoLiquidacion cargoLiquidacion : cargoLiquidacionService.findAllByLegajo(legajoId, anho, mes)) {
            calcularConceptosPorCargo(legajoId, anho, mes, cargoLiquidacion, state);
        }
    }

    private void calcularAdicionalesPorDependencia(Long legajoId, Integer anho, Integer mes, Map<Integer, BigDecimal> totalDependencia, Map<Integer, Integer> horasDependencia, LiquidacionState state) {
        Map<Integer, Dependencia> dependenciasById = dependenciaService.findAllByIds(totalDependencia.keySet()).stream()
                .collect(Collectors.toMap(Dependencia::getDependenciaId, Function.identity()));
        Set<Integer> facultadIds = dependenciasById.values().stream().map(Dependencia::getFacultadId).collect(Collectors.toSet());
        List<AdicionalCursoTablaEntity> tablasAdicionales = adicionalCursoTablaService.findAllByFacultadesAndPeriodo(facultadIds, anho, mes);
        Map<Integer, AdicionalCursoTablaEntity> tablaPorFacultad = new HashMap<>();
        Map<String, AdicionalCursoTablaEntity> tablaPorFacultadYGeografica = new HashMap<>();
        for (AdicionalCursoTablaEntity tabla : tablasAdicionales) {
            if (tabla.getGeograficaId() == null) {
                tablaPorFacultad.put(tabla.getFacultadId(), tabla);
            } else {
                tablaPorFacultadYGeografica.put(tabla.getFacultadId() + "." + tabla.getGeograficaId(), tabla);
            }
        }

        for (Integer dependenciaId : totalDependencia.keySet()) {
            Dependencia dependencia = dependenciasById.get(dependenciaId);
            if (dependencia == null) continue;

            AdicionalCursoTablaEntity adicionalCursoTabla = tablaPorFacultadYGeografica.get(dependencia.getFacultadId() + "." + dependencia.getGeograficaId());
            if (adicionalCursoTabla == null) {
                adicionalCursoTabla = tablaPorFacultad.get(dependencia.getFacultadId());
            }

            if (adicionalCursoTabla != null) {
                Integer horas = horasDependencia.getOrDefault(dependenciaId, 0);
                BigDecimal totalCategoria = totalDependencia.get(dependenciaId);
                BigDecimal porcentaje = BigDecimal.ZERO;
                for (AdicionalCursoRangoEntity adicionalCursoRango : Objects.requireNonNull(adicionalCursoTabla.getAdicionalCursoRangos())) {
                    if (horas >= adicionalCursoRango.getHorasDesde() && horas <= adicionalCursoRango.getHorasHasta()) {
                        porcentaje = adicionalCursoRango.getPorcentaje();
                        break;
                    }
                }
                BigDecimal adicional = totalCategoria.multiply(porcentaje).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                if (adicional.compareTo(BigDecimal.ZERO) > 0) {
                    liquidacionAdicionalService.add(new LiquidacionAdicionalEntity(null, legajoId, anho, mes, dependenciaId, adicional, null, null));
                    addItem(legajoId, anho, mes, CODIGO_BASICO, adicional, state);
                    BigDecimal antiguedad = adicional.multiply(state.getIndices().getFirst()).setScale(2, RoundingMode.HALF_UP);
                    if (antiguedad.compareTo(BigDecimal.ZERO) != 0) {
                        addItem(legajoId, anho, mes, CODIGO_ANTIGUEDAD, antiguedad, state);
                    }
                }
            }
        }
    }

    private void calcularConceptosPorCargo(Long legajoId, Integer anho, Integer mes, CargoLiquidacion cargoLiquidacion, LiquidacionState state) {
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
        addItem(legajoId, anho, mes, CODIGO_BASICO, basico, state);

        BigDecimal estadoDocente = categoria.getEstadoDocente();
        if (estadoDocente.compareTo(BigDecimal.ZERO) != 0) {
            addItem(legajoId, anho, mes, CODIGO_ESTADO_DOCENTE, estadoDocente, state);
            valorMes = valorMes.add(estadoDocente);
        }

        BigDecimal antiguedad = BigDecimal.ZERO;
        if (categoria.getNoDocente() == 1) {
            antiguedad = basico.multiply(state.getIndices().get(1).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
        } else if (categoria.getDocente() == 1) {
            antiguedad = basico.multiply(state.getIndices().getFirst()).setScale(2, RoundingMode.HALF_UP);
        }
        if (antiguedad.compareTo(BigDecimal.ZERO) != 0) {
            valorMes = valorMes.add(antiguedad);
            addItem(legajoId, anho, mes, CODIGO_ANTIGUEDAD, antiguedad, state);
        }

        if (esCargoEtec(categoria) && state.getControlEtec() != null) {
            boolean sinPresentismo = state.getEtecCategoriasSinPresentismo()
                    .contains(categoria.getCategoriaId());
            if (CatalogoUniversosCargos.esCategoriaHorasEtec(categoria)) {
                state.getEtecAcumulado().acumularHoras(basico, antiguedad,
                        state.getControlEtec().getPorcentajePresentismo(),
                        state.getControlEtec().getPorcentajeAdicionalCarrera(),
                        state.getPorcentajeArraigoEtec(),
                        horasClaseEtec(legajoId, anho, mes, cargoLiquidacion),
                        sinPresentismo);
            } else {
                state.getEtecAcumulado().acumularPuntos(basico, antiguedad,
                        state.getControlEtec().getPorcentajePresentismo(),
                        state.getControlEtec().getPorcentajeAdicionalCarrera(),
                        state.getPorcentajeArraigoEtec(),
                        sinPresentismo);
            }
        }

        if (categoria.getNoDocente() == 1) {
            BigDecimal presentismo = basico.multiply(new BigDecimal(cargoLiquidacion.getPresentismo()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
            addItem(legajoId, anho, mes, CODIGO_PRESENTISMO, presentismo, state);
            valorMes = valorMes.add(presentismo);

            if (state.getNovedades().containsKey(CODIGO_PRESENTISMO) && "N".equals(state.getNovedades().get(CODIGO_PRESENTISMO).getValue())) {
                BigDecimal descuentoPresentismo = presentismo.negate();
                addItem(legajoId, anho, mes, CODIGO_DESCUENTO_PRESENTISMO, descuentoPresentismo, state);
            }
        }

        if (state.getNovedades().containsKey(CODIGO_HORAS_EXTRAS)) {
            int totalHoras = cargoLiquidacion.getJornada() == 1 ? 100 : 180;
            BigDecimal valorHora = valorMes.divide(new BigDecimal(totalHoras), 2, RoundingMode.HALF_UP);
            BigDecimal horasExtras = valorHora.multiply(state.getNovedades().get(CODIGO_HORAS_EXTRAS).getImporte()).setScale(2, RoundingMode.HALF_UP);
            addItem(legajoId, anho, mes, CODIGO_HORAS_EXTRAS, horasExtras, state);
        }
    }

    /**
     * Agrega los conceptos propios de la ETEC por puntos (985 a 988) calculados sobre los cargos
     * con {@code categoria.puntos > 0}. Corre después del básico y la antigüedad y antes de los
     * totales parciales, para que entren en los grupos remunerativos.
     */
    private void calcularConceptosEtec(Long legajoId, Integer anho, Integer mes, LiquidacionState state) {
        Map<Integer, BigDecimal> conceptos = etecLiquidacionCalculator.calcular(state.getEtecAcumulado(),
                state.getControlEtec());
        BigDecimal descuentoPresentismo = conceptos.get(EtecLiquidacionCalculator.CODIGO_DESCUENTO_PRESENTISMO_ETEC);
        if (descuentoPresentismo != null && descuentoPresentismo.compareTo(BigDecimal.ZERO) != 0
                && !state.getCodigos().containsKey(EtecLiquidacionCalculator.CODIGO_DESCUENTO_PRESENTISMO_ETEC)) {
            throw new IllegalStateException("Falta el código "
                    + EtecLiquidacionCalculator.CODIGO_DESCUENTO_PRESENTISMO_ETEC
                    + " DESCUENTO PRESENTISMO ETEC en el catálogo: aplicar sql/020_codigo_descuento_presentismo_etec.sql");
        }
        for (Map.Entry<Integer, BigDecimal> concepto : conceptos.entrySet()) {
            if (state.getCodigos().containsKey(concepto.getKey())) {
                addItem(legajoId, anho, mes, concepto.getKey(), concepto.getValue(), state);
            }
        }
    }

    private boolean esCargoEtec(Categoria categoria) {
        return CatalogoUniversosCargos.esCategoriaEtec(categoria);
    }

    /**
     * Horas de clase que originaron el cargo por horas ETEC (categoría marcada como
     * {@code etec_horas}), tomadas de los cursos
     * de la facultad 15 del legajo en la sede del cargo. Se recalculan con el mismo agrupador que usa
     * la generación, así que no hace falta persistirlas en el cargo.
     */
    private BigDecimal horasClaseEtec(Long legajoId, Integer anho, Integer mes,
                                      CargoLiquidacion cargoLiquidacion) {
        Integer geograficaId = cargoLiquidacion.getDependencia() == null ? null
                : cargoLiquidacion.getDependencia().getGeograficaId();
        return AgrupadorHorasEtec.horasEnSede(
                cursoCargoService.findAllByLegajo(legajoId, anho, mes), legajoId, geograficaId);
    }

    /**
     * Genera los cargos del universo docente (incluye ETEC, que también es docente) delegando en
     * el orquestador polimórfico de {@code cargo_liquidacion}.
     */
    @Transactional
    public void generateCargosDocentes(Long legajoId, Integer anho, Integer mes) {
        generarCargosLegajoUseCase.generar(Set.of(UniversoCargos.DOCENTE, UniversoCargos.ETEC),
                legajoId, anho, mes);
    }

    /**
     * Genera los cargos del universo no docente delegando en el orquestador polimórfico de
     * {@code cargo_liquidacion}.
     */
    @Transactional
    public void generateCargosNoDocentes(Long legajoId, Integer anho, Integer mes) {
        generarCargosLegajoUseCase.generar(Set.of(UniversoCargos.NO_DOCENTE), legajoId, anho, mes);
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
        List<LegajoControlEntity> legajos = legajoControlService.findAllByPeriodo(anho, mes);
        for (LegajoControlEntity legajoControl : legajos) {
            legajoControl.setLiquidado((byte) 0);
        }
        legajoControlService.saveAll(legajos);
    }

    /**
     * Alias legado del generador SIJP: conserva la firma de {@code MakeLiquidacionController} y
     * escribe el archivo de la variante normal en {@code path.files}. El frontend usa el endpoint
     * {@code GET /api/haberes/core/sijp/{anho}/{mes}/{variante}}, que no toca disco.
     */
    public String generateSIJP(Integer anho, Integer mes) {
        ArchivoSijp archivo = sijpService.generar(anho, mes, VarianteSijp.NORMAL);
        String base = environment.getProperty("path.files", "/tmp/");
        Path destino = Path.of(base, archivo.getNombreArchivo());
        try {
            Files.createDirectories(destino.getParent());
            Files.write(destino, archivo.getContenido());
        } catch (IOException e) {
            throw new SijpException("No se pudo escribir el archivo SIJP: " + e.getMessage());
        }
        return destino.toString();
    }

    private void addItem(Long legajoId, Integer anho, Integer mes, Integer codigoId, BigDecimal importe, LiquidacionState state) {
        log.debug("Processing MakeLiquidacionService.addItem");
        Item item = state.getItems().computeIfAbsent(codigoId, k -> new Item(null, legajoId, anho, mes, k, state.getCodigos().get(k).getNombre(), BigDecimal.ZERO, null, null, null));
        item.setImporte(item.getImporte().add(importe).setScale(2, RoundingMode.HALF_UP));
        log.debug("ItemEntity -> {}", item.jsonify());
    }

    private void setItem(Long legajoId, Integer anho, Integer mes, Integer codigoId, BigDecimal importe, LiquidacionState state) {
        log.debug("Processing MakeLiquidacionService.setItem");
        Item item = state.getItems().computeIfAbsent(codigoId, k -> new Item(null, legajoId, anho, mes, k, state.getCodigos().get(k).getNombre(), BigDecimal.ZERO, null, null, null));
        item.setImporte(importe.setScale(2, RoundingMode.HALF_UP));
        log.debug("Item -> {}", item.jsonify());
    }

    private BigDecimal getItemValue(Integer codigoId, LiquidacionState state) {
        return Optional.ofNullable(state.getItems().get(codigoId)).map(Item::getImporte).orElse(BigDecimal.ZERO);
    }

    private BigDecimal sumarItemsPorGrupo(List<CodigoGrupoEntity> grupo, Set<Integer> codigosExcluidos, LiquidacionState state) {
        return grupo.stream()
                .map(CodigoGrupoEntity::getCodigoId)
                .filter(id -> !codigosExcluidos.contains(id))
                .map(codigoId -> getItemValue(codigoId, state))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<Integer, BigDecimal> calculateTotalDependenciaFusionado(Long legajoId, Integer anho, Integer mes, Map<String, Dependencia> dependenciasBySede) {
        Map<Integer, BigDecimal> totalDependencia = new HashMap<>();
        List<CursoFusion> cursoFusions = cursoFusionService.findAllByLegajoId(legajoId, anho, mes);
        if (cursoFusions.isEmpty()) return totalDependencia;

        Set<Integer> categoriaIds = cursoFusions.stream().map(CursoFusion::getCategoriaId).collect(Collectors.toSet());
        Map<Integer, CategoriaPeriodoEntity> categoriaPeriodos = categoriaPeriodoService.findAllByCategoriaIdsAndPeriodo(categoriaIds, anho, mes).stream().collect(Collectors.toMap(CategoriaPeriodoEntity::getCategoriaId, Function.identity()));
        Map<Integer, Categoria> categorias = categoriaService.findAllByIds(categoriaIds).stream().collect(Collectors.toMap(Categoria::getCategoriaId, Function.identity()));

        for (CursoFusion cursoFusion : cursoFusions) {
            Dependencia dependencia = dependenciasBySede.get(cursoFusion.getFacultadId() + "." + cursoFusion.getGeograficaId());
            if (dependencia == null) continue;
            BigDecimal basico = Optional.ofNullable(categoriaPeriodos.get(cursoFusion.getCategoriaId())).map(CategoriaPeriodoEntity::getBasico)
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

        Map<Integer, CategoriaPeriodoEntity> categoriaPeriodos = categoriaPeriodoService.findAllByCategoriaIdsAndPeriodo(categoriaIds, anho, mes).stream().collect(Collectors.toMap(CategoriaPeriodoEntity::getCategoriaId, Function.identity()));
        Map<Integer, Categoria> categorias = categoriaService.findAllByIds(categoriaIds).stream().collect(Collectors.toMap(Categoria::getCategoriaId, Function.identity()));

        for (CursoCargo cursoCargo : cursoCargos) {
            if (cursoCargo.getCategoriaId() != null) {
                Curso curso = cursoCargo.getCurso();
                if (curso == null) continue;
                Dependencia dependencia = dependenciasBySede.get(curso.getFacultadId() + "." + curso.getGeograficaId());
                if (dependencia == null) continue;
                BigDecimal basico = Optional.ofNullable(categoriaPeriodos.get(cursoCargo.getCategoriaId())).map(CategoriaPeriodoEntity::getBasico)
                        .orElseGet(() -> Optional.ofNullable(categorias.get(cursoCargo.getCategoriaId())).map(Categoria::getBasico).orElse(BigDecimal.ZERO));
                totalDependencia.compute(dependencia.getDependenciaId(), (k, v) -> (v == null) ? basico : v.add(basico));
            }
        }
        totalDependencia.replaceAll((k, v) -> v.setScale(2, RoundingMode.HALF_UP));
        return totalDependencia;
    }

    /**
     * Jubilación secundaria ETEC: corresponde el 2% adicional cuando el legajo dicta más horas
     * semanales en la ETEC (facultad 15) que en el resto de las facultades. Se comparan las horas
     * de {@code curso_cargo} del período; no se suman cargos con clase ni las horas fijas del
     * cargo por puntos. Exige que el legajo tenga al menos un cargo ETEC liquidado.
     */
    private boolean evaluarJubilacionSecundaria(Long legajoId, Integer anho, Integer mes,
                                                LiquidacionState state) {
        return state.getEtecAcumulado().hayCargos()
                && tieneMasHorasEtecQueOtras(cursoCargoService.findAllByLegajo(legajoId, anho, mes));
    }

    static boolean tieneMasHorasEtecQueOtras(List<CursoCargo> cursos) {
        BigDecimal horasEtec = BigDecimal.ZERO;
        BigDecimal horasOtras = BigDecimal.ZERO;
        for (CursoCargo cursoCargo : cursos) {
            Curso curso = cursoCargo.getCurso();
            if (curso == null || curso.getFacultadId() == null) {
                continue;
            }
            BigDecimal horas = cursoCargo.getHorasSemanales() == null
                    ? BigDecimal.ZERO : cursoCargo.getHorasSemanales();
            if (AgrupadorHorasEtec.FACULTAD_ETEC.equals(curso.getFacultadId())) {
                horasEtec = horasEtec.add(horas);
            } else {
                horasOtras = horasOtras.add(horas);
            }
        }
        return horasEtec.compareTo(BigDecimal.ZERO) > 0
                && horasEtec.compareTo(horasOtras) > 0;
    }

    public boolean evaluateOnlyETEC(LiquidacionState state) {
        boolean hasCargoClaseInFacultades = false;
        for (CargoClaseDetalleEntity detalle : state.getCargoClases()) {
            if (detalle.getFacultadId() != 6) {
                hasCargoClaseInFacultades = true;
            }
        }
        Map<Integer, Item> items = state.getItems();
        boolean hasBasico = hasCargoClaseInFacultades && items.containsKey(CODIGO_BASICO) && items.get(CODIGO_BASICO).getImporte().compareTo(BigDecimal.ZERO) != 0;
        boolean hasETEC = items.containsKey(CODIGO_AGUINALDO_ETEC) && items.get(CODIGO_AGUINALDO_ETEC).getImporte().compareTo(BigDecimal.ZERO) != 0;
        return hasETEC && !hasBasico;
    }

}
