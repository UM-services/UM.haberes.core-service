package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.audit.AuditBulkScope;
import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.audit.BulkContext;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.DeleteCargosNotInByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class DeleteCargosNotInByPeriodoUseCaseImpl implements DeleteCargosNotInByPeriodoUseCase {

    private final CargoLiquidacionRepository cargoLiquidacionRepository;
    private final AuditBulkService auditBulk;

    @Transactional
    @Override
    public void deleteCargosNotInByPeriodo(List<Long> legajoIds, Integer anho, Integer mes) {
        try (AuditBulkScope ignored = auditBulk.begin(
                BulkContext.of("depuracion-cargos-periodo", anho, mes, null))) {
            cargoLiquidacionRepository.deleteAllByLegajoIdNotInAndAnhoAndMes(legajoIds, anho, mes);
        }
    }
}
