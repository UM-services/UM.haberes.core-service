package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.audit.AuditBulkScope;
import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.audit.BulkContext;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.DeleteCargosNotInByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@Component
@RequiredArgsConstructor
public class DeleteCargosNotInByPeriodoUseCaseImpl implements DeleteCargosNotInByPeriodoUseCase {

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    private final CategoriaRepository categoriaRepository;

    private final AuditBulkService auditBulk;

    @Transactional
    @Override
    public void deleteCargosNotInByPeriodo(List<Long> legajoIds, Integer anho, Integer mes) {
        // Los cargos ETEC no se depuran con la planilla de cargos: dependen de la asignación
        // vigente en etec_asignacion_cargo, no de que el legajo tenga liquidación en el período.
        List<Integer> categoriasEtec = categoriaRepository.findAllByPeriodo(anho, mes).stream()
                .filter(categoria -> categoria.getPuntos() != null
                        && categoria.getPuntos().compareTo(BigDecimal.ZERO) > 0)
                .map(Categoria::getCategoriaId)
                .filter(categoriaId -> categoriaId != null)
                .toList();
        try (AuditBulkScope ignored = auditBulk.begin(
                BulkContext.of("depuracion-cargos-periodo", anho, mes, null))) {
            cargoLiquidacionRepository.deleteAllByLegajoIdNotInAndAnhoAndMesAndCategoriaIdNotIn(legajoIds,
                    anho, mes, categoriasEtec);
        }
    }
}
