package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.audit.AuditBulkScope;
import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.audit.BulkContext;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.DeleteItemsByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;

@Component
@RequiredArgsConstructor
public class DeleteItemsByLegajoUseCaseImpl implements DeleteItemsByLegajoUseCase {

    private final ItemRepository itemRepository;
    private final AuditBulkService auditBulk;

    @Transactional
    @Override
    public void deleteItemsByLegajo(Long legajoId, Integer anho, Integer mes) {
        try (AuditBulkScope ignored = auditBulk.begin(
                BulkContext.of("borrado-items-legajo", anho, mes, legajoId))) {
            itemRepository.deleteAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
        }
    }
}
