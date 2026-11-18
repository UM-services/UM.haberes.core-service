package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.audit.AuditBulkScope;
import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.audit.BulkContext;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.exception.CargoLiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.ReplaceCargosByCategoriaUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.SaveAllCargoLiquidacionesUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

/**
 * Borra los cargos de las categorías indicadas (período o legajo/período) y guarda el lote
 * recibido. El guardado usa {@link SaveAllCargoLiquidacionesUseCase} con {@code version = null}
 * para que se genere el snapshot de {@code cargo_liquidacion_version}.
 */
@Component
@RequiredArgsConstructor
public class ReplaceCargosByCategoriaUseCaseImpl implements ReplaceCargosByCategoriaUseCase {

    private final CargoLiquidacionRepository cargoLiquidacionRepository;
    private final SaveAllCargoLiquidacionesUseCase saveAllCargoLiquidacionesUseCase;
    private final AuditBulkService auditBulk;

    @Transactional
    @Override
    public List<CargoLiquidacion> replaceByPeriodo(Integer anho, Integer mes, List<Integer> categoriaIds,
            List<CargoLiquidacion> cargos) {
        validar(anho, mes, categoriaIds, cargos, null);
        try (AuditBulkScope ignored = auditBulk.begin(
                BulkContext.of("reemplazo-cargos-periodo", anho, mes, null))) {
            cargoLiquidacionRepository.deleteAllByAnhoAndMesAndCategoriaIdIn(anho, mes, categoriaIds);
        }
        return saveAllCargoLiquidacionesUseCase.saveAllCargoLiquidaciones(cargos, null);
    }

    @Transactional
    @Override
    public List<CargoLiquidacion> replaceByLegajo(Long legajoId, Integer anho, Integer mes,
            List<Integer> categoriaIds, List<CargoLiquidacion> cargos) {
        validar(anho, mes, categoriaIds, cargos, legajoId);
        try (AuditBulkScope ignored = auditBulk.begin(
                BulkContext.of("reemplazo-cargos-legajo", anho, mes, legajoId))) {
            cargoLiquidacionRepository.deleteAllByLegajoIdAndAnhoAndMesAndCategoriaIdIn(legajoId, anho, mes,
                    categoriaIds);
        }
        return saveAllCargoLiquidacionesUseCase.saveAllCargoLiquidaciones(cargos, null);
    }

    private void validar(Integer anho, Integer mes, List<Integer> categoriaIds, List<CargoLiquidacion> cargos,
            Long legajoId) {
        if (categoriaIds == null || categoriaIds.isEmpty()) {
            throw new CargoLiquidacionException("Debe indicar al menos una categoría a reemplazar");
        }
        if (cargos == null) {
            throw new CargoLiquidacionException("El lote de cargos es obligatorio");
        }
        for (CargoLiquidacion cargo : cargos) {
            if (!Objects.equals(cargo.getAnho(), anho) || !Objects.equals(cargo.getMes(), mes)) {
                throw new CargoLiquidacionException(
                        "El cargo " + cargo.getLegajoId() + " no pertenece al período " + anho + "/" + mes);
            }
            if (legajoId != null && !Objects.equals(cargo.getLegajoId(), legajoId)) {
                throw new CargoLiquidacionException(
                        "El cargo " + cargo.getLegajoId() + " no pertenece al legajo " + legajoId);
            }
            if (cargo.getCategoriaId() == null || !categoriaIds.contains(cargo.getCategoriaId())) {
                throw new CargoLiquidacionException(
                        "La categoría " + cargo.getCategoriaId() + " no está en el alcance del reemplazo");
            }
        }
    }
}
