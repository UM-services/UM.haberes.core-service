package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.audit.AuditBulkScope;
import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.audit.BulkContext;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ContextoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ControlGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ResultadoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.UniversoCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneracionCargosDatosPort;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneradorCargosLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.SaveAllCargoLiquidacionesUseCase;

/**
 * Orquestador de la generación de cargos.
 *
 * <p>No conoce ninguna fuente: recibe todas las implementaciones de
 * {@link GeneradorCargosLiquidacion}, descarta las que no aplican al contexto, une el alcance de
 * borrado de los universos pedidos y guarda todo en un único {@code saveAll}, que produce un
 * único snapshot en {@code cargo_liquidacion_version}.</p>
 */
@Component
@RequiredArgsConstructor
public class GeneracionCargosOrquestador {

    private static final String SITUACION_ACTIVA = "A";

    private static final String LABEL_BORRADO = "borrado-cargos-generacion";

    private final List<GeneradorCargosLiquidacion> generadores;

    private final CatalogoUniversosCargos catalogo;

    private final GeneracionCargosDatosPort datos;

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    private final SaveAllCargoLiquidacionesUseCase saveAllCargoLiquidacionesUseCase;

    private final AuditBulkService auditBulk;

    /** Calcula, borra el alcance y persiste. Debe invocarse dentro de una transacción. */
    public ResultadoGeneracionCargos generar(Set<UniversoCargos> universos, Long legajoId, Integer anho,
                                             Integer mes) {
        ContextoGeneracionCargos contexto = contexto(legajoId, anho, mes);
        List<CargoLiquidacion> cargos = new ArrayList<>();
        Map<UniversoCargos, Integer> conteos = new EnumMap<>(UniversoCargos.class);

        for (GeneradorCargosLiquidacion generador : generadores) {
            if (!universos.contains(generador.universo()) || !generador.aplica(contexto)) {
                continue;
            }
            List<CargoLiquidacion> generados = generador.cargos(contexto);
            cargos.addAll(generados);
            conteos.merge(generador.universo(), generados.size(), Integer::sum);
        }

        eliminar(universos, legajoId, anho, mes);
        return new ResultadoGeneracionCargos(
                saveAllCargoLiquidacionesUseCase.saveAllCargoLiquidaciones(cargos, null), conteos);
    }

    /** Calcula las filas sin tocar la base. */
    public ResultadoGeneracionCargos previsualizar(Set<UniversoCargos> universos, Long legajoId,
                                                   Integer anho, Integer mes) {
        ContextoGeneracionCargos contexto = contexto(legajoId, anho, mes);
        List<CargoLiquidacion> cargos = new ArrayList<>();
        Map<UniversoCargos, Integer> conteos = new EnumMap<>(UniversoCargos.class);

        for (GeneradorCargosLiquidacion generador : generadores) {
            if (!universos.contains(generador.universo()) || !generador.aplica(contexto)) {
                continue;
            }
            List<CargoLiquidacion> generados = generador.cargos(contexto);
            cargos.addAll(generados);
            conteos.merge(generador.universo(), generados.size(), Integer::sum);
        }
        return new ResultadoGeneracionCargos(cargos, conteos);
    }

    private void eliminar(Set<UniversoCargos> universos, Long legajoId, Integer anho, Integer mes) {
        List<Integer> categorias = catalogo.categoriaIds(universos,
                universos.contains(UniversoCargos.ETEC) ? datos.categoriasEtecAsignadas() : List.of());
        if (categorias.isEmpty()) {
            return;
        }
        try (AuditBulkScope ignored = auditBulk.begin(BulkContext.of(LABEL_BORRADO, anho, mes, legajoId))) {
            if (legajoId != null) {
                cargoLiquidacionRepository.deleteAllByLegajoIdAndAnhoAndMesAndSituacionAndCategoriaIdIn(
                        legajoId, anho, mes, SITUACION_ACTIVA, categorias);
            } else {
                cargoLiquidacionRepository.deleteAllByAnhoAndMesAndCategoriaIdIn(anho, mes, categorias);
            }
        }
    }

    private ContextoGeneracionCargos contexto(Long legajoId, Integer anho, Integer mes) {
        ControlGeneracionCargos control = datos.control(anho, mes);
        return new ContextoGeneracionCargos(legajoId, anho, mes, control.modoLiquidacionId(),
                control.valorPuntoEtec(), control.montoFijoCargo(), datos.dependenciasPorSede());
    }
}
