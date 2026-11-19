package um.haberes.core.hexagonal.liquidaciones.general.application.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.CargoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.model.CargoLiquidacionView;
import um.haberes.core.hexagonal.crossslice.legajo_control.LegajoControlCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.liquidacion_proceso.ProcesoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.novedad.NovedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.persona.PersonaCrossSlice;
import um.haberes.core.hexagonal.crossslice.persona.model.PersonaView;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.MotivoResultadoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ResultadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.TipoProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.ports.out.ProcesoGeneralRepository;
import um.haberes.core.hexagonal.liquidaciones.reglas.Inelegibilidad;
import um.haberes.core.hexagonal.liquidaciones.reglas.MotivoInelegibilidad;
import um.haberes.core.hexagonal.liquidaciones.reglas.ReglasElegibilidadLiquidacion;

/**
 * Ejecutor asíncrono de los procesos masivos de liquidación general.
 *
 * <p>Es un bean separado a propósito: {@code @Async} no se aplica en llamadas internas dentro del
 * mismo bean. Procesa los legajos con concurrencia acotada ({@code haberes.liquidacion-general.
 * concurrencia}, por defecto 2) y persiste avance y detalle a medida que termina cada uno.</p>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProcesoGeneralRunner {

    private static final int MAX_DETALLE = 500;

    private final ProcesoGeneralRepository procesoGeneralRepository;

    private final PersonaCrossSlice personaCrossSlice;

    private final LiquidacionCrossSlice liquidacionCrossSlice;

    private final LegajoControlCrossSlice legajoControlCrossSlice;

    private final ReglasElegibilidadLiquidacion reglas;

    private final ProcesoLiquidacionCrossSlice procesoLiquidacionCrossSlice;

    private final CargoLiquidacionCrossSlice cargoLiquidacionCrossSlice;

    private final Object lock = new Object();

    @Value("${haberes.liquidacion-general.concurrencia:2}")
    private int concurrencia = 2;

    @Async
    public void ejecutar(String procesoId) {
        Optional<ProcesoGeneral> encontrado = procesoGeneralRepository.findById(procesoId);
        if (encontrado.isEmpty()) {
            log.warn("Proceso de liquidación general inexistente: {}", procesoId);
            return;
        }
        ProcesoGeneral proceso = encontrado.get();
        AtomicInteger procesados = new AtomicInteger();
        AtomicInteger exitosos = new AtomicInteger();
        AtomicInteger omitidos = new AtomicInteger();
        AtomicInteger fallidos = new AtomicInteger();

        try {
            List<PersonaView> universo = personaCrossSlice.liquidables();
            proceso = conTotal(proceso, universo.size());
            procesoGeneralRepository.save(proceso);

            if (proceso.tipo() == TipoProcesoGeneral.LIQUIDAR) {
                legajoControlCrossSlice.desmarcarPeriodo(proceso.anho(), proceso.mes());
            }

            ProcesoGeneral contexto = proceso;
            ExecutorService executor = Executors.newFixedThreadPool(Math.max(1, concurrencia));
            try {
                List<Future<?>> futuros = new java.util.ArrayList<>();
                for (PersonaView persona : universo) {
                    futuros.add(executor.submit(() -> procesarLegajo(contexto, persona, procesados, exitosos,
                            omitidos, fallidos)));
                }
                for (Future<?> futuro : futuros) {
                    try {
                        futuro.get();
                    } catch (Exception e) {
                        log.warn("Error al esperar un legajo del proceso {}: {}", procesoId, e.getMessage());
                    }
                }
            } finally {
                executor.shutdown();
            }

            actualizar(proceso, procesados.get(), exitosos.get(), omitidos.get(), fallidos.get(),
                    EstadoProcesoGeneral.COMPLETADO, "Proceso completado.", true);
        } catch (Exception e) {
            log.error("El proceso de liquidación general {} falló", procesoId, e);
            actualizar(proceso, procesados.get(), exitosos.get(), omitidos.get(), fallidos.get(),
                    EstadoProcesoGeneral.FALLIDO, truncar("El proceso falló: " + mensaje(e)), true);
        }
    }

    private void procesarLegajo(ProcesoGeneral proceso, PersonaView persona, AtomicInteger procesados,
                                AtomicInteger exitosos, AtomicInteger omitidos, AtomicInteger fallidos) {
        ResultadoLegajoGeneral resultado;
        try {
            resultado = procesar(proceso, persona);
        } catch (RuntimeException e) {
            log.warn("Legajo {} del proceso {} falló: {}", persona.legajoId(), proceso.procesoId(), mensaje(e));
            resultado = resultado(persona, EstadoLegajoGeneral.FALLIDO, MotivoResultadoGeneral.ERROR,
                    truncar(mensaje(e)), null);
        }
        try {
            procesoGeneralRepository.saveDetalle(proceso.procesoId(), resultado);
        } catch (RuntimeException e) {
            log.error("No se pudo guardar el detalle del legajo {} del proceso {}", persona.legajoId(),
                    proceso.procesoId(), e);
        }
        switch (resultado.resultado()) {
            case OMITIDO -> omitidos.incrementAndGet();
            case FALLIDO -> fallidos.incrementAndGet();
            default -> exitosos.incrementAndGet();
        }
        procesados.incrementAndGet();
        actualizar(proceso, procesados.get(), exitosos.get(), omitidos.get(), fallidos.get(),
                EstadoProcesoGeneral.EN_CURSO, null, false);
    }

    private ResultadoLegajoGeneral procesar(ProcesoGeneral proceso, PersonaView persona) {
        return switch (proceso.tipo()) {
            case LIQUIDAR -> liquidar(proceso, persona);
            case CARGOS -> generarCargos(proceso, persona);
            case ELIMINAR -> eliminar(proceso, persona);
        };
    }

    private ResultadoLegajoGeneral liquidar(ProcesoGeneral proceso, PersonaView persona) {
        Long legajoId = persona.legajoId();
        Integer anho = proceso.anho();
        Integer mes = proceso.mes();
        Optional<LiquidacionView> liquidacion = liquidacionCrossSlice.porClaveUnica(legajoId, anho, mes);

        Optional<Inelegibilidad> bloqueo = reglas.bloqueo(liquidacion, proceso.forzar());
        if (bloqueo.isPresent()) {
            return resultado(persona, EstadoLegajoGeneral.OMITIDO, MotivoResultadoGeneral.BLOQUEADA,
                    bloqueo.get().mensaje(), null);
        }
        if (!reglas.hayControl(anho, mes)) {
            return resultado(persona, EstadoLegajoGeneral.OMITIDO, MotivoResultadoGeneral.SIN_CONTROL,
                    "Faltan datos de control del período.", null);
        }
        Optional<Inelegibilidad> inelegibilidad = reglas.validarPersona(legajoId, anho, mes);
        if (inelegibilidad.isPresent()) {
            return resultado(persona, EstadoLegajoGeneral.OMITIDO,
                    motivo(inelegibilidad.get().motivo()), inelegibilidad.get().mensaje(), null);
        }
        if (!reglas.periodoLiquidable(legajoId, anho, mes, reglas.novedades(legajoId, anho, mes))) {
            return resultado(persona, EstadoLegajoGeneral.OMITIDO, MotivoResultadoGeneral.PERIODO_NO_LIQUIDABLE,
                    "El período no tiene cargos ni novedades liquidables.", null);
        }

        procesoLiquidacionCrossSlice.liquidar(legajoId, anho, mes, proceso.forzar());

        Optional<LiquidacionView> generada = liquidacionCrossSlice.porClaveUnica(legajoId, anho, mes);
        if (generada.isEmpty()) {
            return resultado(persona, EstadoLegajoGeneral.FALLIDO, MotivoResultadoGeneral.NO_LIQUIDABLE,
                    "No se generó la liquidación del legajo; revise los datos del período.", null);
        }
        BigDecimal neto = generada.get().totalNeto() == null ? BigDecimal.ZERO : generada.get().totalNeto();
        return resultado(persona, EstadoLegajoGeneral.LIQUIDADO, null,
                "Liquidación generada correctamente.", neto);
    }

    private ResultadoLegajoGeneral generarCargos(ProcesoGeneral proceso, PersonaView persona) {
        Long legajoId = persona.legajoId();
        Integer anho = proceso.anho();
        Integer mes = proceso.mes();
        Optional<LiquidacionView> liquidacion = liquidacionCrossSlice.porClaveUnica(legajoId, anho, mes);

        // La generación de cargos nunca fuerza: el legajo bloqueado se omite y se reporta.
        Optional<Inelegibilidad> bloqueo = reglas.bloqueo(liquidacion, false);
        if (bloqueo.isPresent()) {
            return resultado(persona, EstadoLegajoGeneral.OMITIDO, MotivoResultadoGeneral.BLOQUEADA,
                    bloqueo.get().mensaje(), null);
        }

        procesoLiquidacionCrossSlice.generarCargos(legajoId, anho, mes);

        List<CargoLiquidacionView> cargos = cargoLiquidacionCrossSlice.porLegajo(legajoId, anho, mes);
        int docentes = (int) cargos.stream().filter(CargoLiquidacionView::docente).count();
        int noDocentes = cargos.size() - docentes;
        return resultado(persona, EstadoLegajoGeneral.GENERADO, null,
                "Cargos generados: " + docentes + " docente(s) y " + noDocentes + " no docente(s).", null);
    }

    private ResultadoLegajoGeneral eliminar(ProcesoGeneral proceso, PersonaView persona) {
        Long legajoId = persona.legajoId();
        Integer anho = proceso.anho();
        Integer mes = proceso.mes();
        Optional<LiquidacionView> liquidacion = liquidacionCrossSlice.porClaveUnica(legajoId, anho, mes);

        Optional<Inelegibilidad> bloqueo = reglas.bloqueo(liquidacion, proceso.forzar());
        if (bloqueo.isPresent()) {
            return resultado(persona, EstadoLegajoGeneral.OMITIDO, MotivoResultadoGeneral.BLOQUEADA,
                    bloqueo.get().mensaje(), null);
        }

        procesoLiquidacionCrossSlice.eliminarLiquidacion(legajoId, anho, mes, proceso.forzar());

        if (liquidacionCrossSlice.porClaveUnica(legajoId, anho, mes).isPresent()) {
            return resultado(persona, EstadoLegajoGeneral.FALLIDO, MotivoResultadoGeneral.NO_LIQUIDABLE,
                    "No se eliminó la liquidación del legajo; revise los datos del período.", null);
        }
        return resultado(persona, EstadoLegajoGeneral.ELIMINADO, null,
                "Liquidación eliminada correctamente.", null);
    }

    private ResultadoLegajoGeneral resultado(PersonaView persona, EstadoLegajoGeneral estado,
                                             MotivoResultadoGeneral motivo, String detalle, BigDecimal totalNeto) {
        return new ResultadoLegajoGeneral(null, persona.legajoId(), persona.apellido(), persona.nombre(),
                estado, motivo, truncar(detalle), totalNeto, OffsetDateTime.now());
    }

    private MotivoResultadoGeneral motivo(MotivoInelegibilidad motivo) {
        return switch (motivo) {
            case BLOQUEADA -> MotivoResultadoGeneral.BLOQUEADA;
            case PERSONA_INEXISTENTE -> MotivoResultadoGeneral.PERSONA_INEXISTENTE;
            case SIN_LIQUIDACION_ANTERIOR -> MotivoResultadoGeneral.SIN_LIQUIDACION_ANTERIOR;
            case PERSONA_NO_LIQUIDABLE -> MotivoResultadoGeneral.PERSONA_NO_LIQUIDABLE;
        };
    }

    private void actualizar(ProcesoGeneral proceso, int procesados, int exitosos, int omitidos, int fallidos,
                            EstadoProcesoGeneral estado, String mensaje, boolean finalizado) {
        synchronized (lock) {
            ProcesoGeneral actualizado = new ProcesoGeneral(proceso.procesoId(), proceso.tipo(), proceso.anho(),
                    proceso.mes(), estado, proceso.total(), procesados, exitosos, omitidos, fallidos,
                    proceso.forzar(), proceso.iniciadoPor(), proceso.iniciadoEn(),
                    finalizado ? OffsetDateTime.now() : null, mensaje);
            procesoGeneralRepository.save(actualizado);
        }
    }

    private ProcesoGeneral conTotal(ProcesoGeneral proceso, int total) {
        return new ProcesoGeneral(proceso.procesoId(), proceso.tipo(), proceso.anho(), proceso.mes(),
                proceso.estado(), total, proceso.procesados(), proceso.exitosos(), proceso.omitidos(),
                proceso.fallidos(), proceso.forzar(), proceso.iniciadoPor(), proceso.iniciadoEn(),
                proceso.finalizadoEn(), proceso.mensaje());
    }

    private String truncar(String mensaje) {
        if (mensaje == null) {
            return null;
        }
        return mensaje.length() <= MAX_DETALLE ? mensaje : mensaje.substring(0, MAX_DETALLE);
    }

    private String mensaje(Throwable e) {
        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
    }
}
