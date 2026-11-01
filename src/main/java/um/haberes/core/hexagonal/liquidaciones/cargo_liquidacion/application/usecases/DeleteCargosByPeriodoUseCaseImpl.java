package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.audit.AuditBulkScope;
import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.audit.BulkContext;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.DeleteCargosByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class DeleteCargosByPeriodoUseCaseImpl implements DeleteCargosByPeriodoUseCase {

    private final CargoLiquidacionRepository cargoLiquidacionRepository;
    private final AuditBulkService auditBulk;

    @Transactional
    @Override
    public void deleteCargosByPeriodo(Integer anho, Integer mes) {
        try (AuditBulkScope ignored = auditBulk.begin(
                BulkContext.of("borrado-cargos-periodo", anho, mes, null))) {
            cargoLiquidacionRepository.deleteAllByAnhoAndMes(anho, mes);
        }
    }
}
