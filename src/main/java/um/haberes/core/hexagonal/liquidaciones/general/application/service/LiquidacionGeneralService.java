package um.haberes.core.hexagonal.liquidaciones.general.application.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.CargoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.model.CargoLiquidacionView;
import um.haberes.core.hexagonal.crossslice.legajo_control.LegajoControlCrossSlice;
import um.haberes.core.hexagonal.crossslice.legajo_control.model.LegajoControlView;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.novedad.NovedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.persona.PersonaCrossSlice;
import um.haberes.core.hexagonal.crossslice.persona.model.PersonaView;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.AccionesLiquidacionGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.FilaLiquidacionGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.InicioProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.PadronLiquidacionGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ResultadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ResumenPadron;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.TipoProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.ports.in.IniciarProcesoCargosUseCase;
import um.haberes.core.hexagonal.liquidaciones.general.domain.ports.in.IniciarProcesoEliminarUseCase;
import um.haberes.core.hexagonal.liquidaciones.general.domain.ports.in.IniciarProcesoLiquidarUseCase;
import um.haberes.core.hexagonal.liquidaciones.general.domain.ports.in.ListarProcesosPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.general.domain.ports.in.ObtenerDetalleProcesoUseCase;
import um.haberes.core.hexagonal.liquidaciones.general.domain.ports.in.ObtenerPadronGeneralUseCase;
import um.haberes.core.hexagonal.liquidaciones.general.domain.ports.in.ObtenerProcesoGeneralUseCase;
import um.haberes.core.hexagonal.liquidaciones.general.domain.ports.out.ProcesoGeneralRepository;
import um.haberes.core.hexagonal.liquidaciones.reglas.ReglasElegibilidadLiquidacion;

/**
 * Fachada de aplicación de la liquidación general.
 *
 * <p>Arma el padrón agregado del período y encola los procesos masivos. La ejecución corre en
 * {@link ProcesoGeneralRunner}; acá sólo se valida el precondicionamiento y se persiste la
 * cabecera.</p>
 */
@Component
@RequiredArgsConstructor
public class LiquidacionGeneralService implements ObtenerPadronGeneralUseCase, IniciarProcesoCargosUseCase,
        IniciarProcesoLiquidarUseCase, IniciarProcesoEliminarUseCase, ObtenerProcesoGeneralUseCase,
        ObtenerDetalleProcesoUseCase, ListarProcesosPeriodoUseCase {

    public static final String PROCESO_EN_CURSO = "PROCESO_EN_CURSO";
    public static final String SIN_CONTROL = "SIN_CONTROL";
    public static final String SIN_LIQUIDABLES = "SIN_LIQUIDABLES";

    private static final byte BLOQUEADO = 1;
    private static final byte LIQUIDADO = 1;

    private final PersonaCrossSlice personaCrossSlice;

    private final CargoLiquidacionCrossSlice cargoLiquidacionCrossSlice;

    private final LiquidacionCrossSlice liquidacionCrossSlice;

    private final NovedadCrossSlice novedadCrossSlice;

    private final LegajoControlCrossSlice legajoControlCrossSlice;

    private final ReglasElegibilidadLiquidacion reglas;

    private final ProcesoGeneralRepository procesoGeneralRepository;

    private final ProcesoGeneralRunner procesoGeneralRunner;

    @Override
    public PadronLiquidacionGeneral obtener(Integer anho, Integer mes) {
        List<PersonaView> personas = personaCrossSlice.liquidables();

        Map<Long, List<CargoLiquidacionView>> cargosPorLegajo = new HashMap<>();
        for (CargoLiquidacionView cargo : cargoLiquidacionCrossSlice.porPeriodo(anho, mes)) {
            cargosPorLegajo.computeIfAbsent(cargo.legajoId(), key -> new ArrayList<>()).add(cargo);
        }

        Map<Long, LiquidacionView> liquidaciones = new LinkedHashMap<>();
        for (LiquidacionView liquidacion : liquidacionCrossSlice.porPeriodo(anho, mes)) {
            // La clave única legajo/período no debería repetirse, pero el merge evita romper el padrón.
            liquidaciones.merge(liquidacion.legajoId(), liquidacion, (primera, segunda) -> primera);
        }

        Map<Long, LegajoControlView> controles = new LinkedHashMap<>();
        for (LegajoControlView control : legajoControlCrossSlice.porPeriodo(anho, mes)) {
            controles.putIfAbsent(control.legajoId(), control);
        }

        Set<Long> legajosConNovedades = new HashSet<>(novedadCrossSlice.legajosConNovedades(anho, mes));
        boolean hayControl = reglas.hayControl(anho, mes);

        List<FilaLiquidacionGeneral> filas = new ArrayList<>(personas.size());
        int docentes = 0;
        int noDocentes = 0;
        int liquidados = 0;
        int bloqueados = 0;
        int netoNegativo = 0;
        int netoCero = 0;
        int conNovedades = 0;
        BigDecimal totalNeto = BigDecimal.ZERO;

        List<PersonaView> ordenadas = personas.stream()
                .sorted(Comparator.comparing(PersonaView::apellido, Comparator.nullsLast(String::compareToIgnoreCase))
                        .thenComparing(PersonaView::nombre, Comparator.nullsLast(String::compareToIgnoreCase)))
                .toList();

        int orden = 1;
        for (PersonaView persona : ordenadas) {
            List<CargoLiquidacionView> cargos = cargosPorLegajo.getOrDefault(persona.legajoId(), List.of());
            boolean esDocente = cargos.stream().anyMatch(CargoLiquidacionView::docente);
            boolean esNoDocente = cargos.stream().anyMatch(cargo -> !cargo.docente());
            LiquidacionView liquidacion = liquidaciones.get(persona.legajoId());
            LegajoControlView control = controles.get(persona.legajoId());

            boolean bloqueado = liquidacion != null && liquidacion.bloqueado() != null
                    && liquidacion.bloqueado() == BLOQUEADO;
            boolean liquidado = control != null && control.liquidado() != null
                    && control.liquidado() == LIQUIDADO;

            BigDecimal remunerativo = valor(liquidacion == null ? null : liquidacion.totalRemunerativo());
            BigDecimal noRemunerativo = valor(liquidacion == null ? null : liquidacion.totalNoRemunerativo());
            BigDecimal deduccion = valor(liquidacion == null ? null : liquidacion.totalDeduccion());
            BigDecimal neto = valor(liquidacion == null ? null : liquidacion.totalNeto());

            if (esDocente) {
                docentes++;
            }
            if (esNoDocente) {
                noDocentes++;
            }
            if (liquidado) {
                liquidados++;
            }
            if (bloqueado) {
                bloqueados++;
            }
            if (neto.signum() < 0) {
                netoNegativo++;
            }
            if (liquidacion != null && neto.signum() == 0) {
                netoCero++;
            }
            boolean tieneNovedades = legajosConNovedades.contains(persona.legajoId());
            if (tieneNovedades) {
                conNovedades++;
            }
            totalNeto = totalNeto.add(neto);

            filas.add(new FilaLiquidacionGeneral(orden++, persona.legajoId(), persona.apellido(),
                    persona.nombre(), persona.estado(), persona.liquida(), persona.dependenciaId(),
                    persona.salida(), esDocente, esNoDocente, tieneNovedades,
                    liquidado, bloqueado, remunerativo, noRemunerativo, deduccion, neto));
        }

        ResumenPadron resumen = new ResumenPadron(filas.size(), docentes, noDocentes, conNovedades,
                liquidados, bloqueados, netoNegativo, netoCero, totalNeto);
        AccionesLiquidacionGeneral acciones = new AccionesLiquidacionGeneral(hayControl, hayControl,
                hayControl, true);
        return new PadronLiquidacionGeneral(anho, mes, acciones, resumen, filas);
    }

    @Override
    public synchronized InicioProcesoGeneral iniciar(Integer anho, Integer mes, Long legajoSolicitante) {
        return iniciar(TipoProcesoGeneral.CARGOS, anho, mes, false, legajoSolicitante);
    }

    @Override
    public InicioProcesoGeneral iniciar(Integer anho, Integer mes, boolean forzar, Long legajoSolicitante) {
        return iniciar(TipoProcesoGeneral.LIQUIDAR, anho, mes, forzar, legajoSolicitante);
    }

    @Override
    public InicioProcesoGeneral eliminar(Integer anho, Integer mes, boolean forzar, Long legajoSolicitante) {
        return iniciar(TipoProcesoGeneral.ELIMINAR, anho, mes, forzar, legajoSolicitante);
    }

    private synchronized InicioProcesoGeneral iniciar(TipoProcesoGeneral tipo, Integer anho, Integer mes,
                                                      boolean forzar, Long legajoSolicitante) {
        if (procesoGeneralRepository.existsEnCurso(anho, mes)) {
            return InicioProcesoGeneral.noIniciado(PROCESO_EN_CURSO,
                    "Ya hay un proceso de liquidación general en curso para el período.");
        }
        if (tipo != TipoProcesoGeneral.ELIMINAR && !reglas.hayControl(anho, mes)) {
            return InicioProcesoGeneral.noIniciado(SIN_CONTROL,
                    "Faltan datos de control del período.");
        }
        int total = personaCrossSlice.liquidables().size();
        if (total == 0) {
            return InicioProcesoGeneral.noIniciado(SIN_LIQUIDABLES,
                    "No hay legajos liquidables en el período.");
        }

        ProcesoGeneral proceso = new ProcesoGeneral(UUID.randomUUID().toString(), tipo, anho, mes,
                EstadoProcesoGeneral.EN_CURSO, total, 0, 0, 0, 0, forzar, legajoSolicitante,
                OffsetDateTime.now(), null, null);
        ProcesoGeneral guardado = procesoGeneralRepository.save(proceso);
        procesoGeneralRunner.ejecutar(guardado.procesoId());
        return InicioProcesoGeneral.iniciado(guardado);
    }

    @Override
    public Optional<ProcesoGeneral> obtener(String procesoId) {
        return procesoGeneralRepository.findById(procesoId);
    }

    @Override
    public List<ResultadoLegajoGeneral> obtener(String procesoId, EstadoLegajoGeneral resultado) {
        return procesoGeneralRepository.findDetalles(procesoId, resultado);
    }

    @Override
    public List<ProcesoGeneral> listar(Integer anho, Integer mes) {
        return procesoGeneralRepository.findAllByAnhoAndMes(anho, mes);
    }

    private BigDecimal valor(BigDecimal valor) {
        return valor == null ? BigDecimal.ZERO : valor;
    }
}
