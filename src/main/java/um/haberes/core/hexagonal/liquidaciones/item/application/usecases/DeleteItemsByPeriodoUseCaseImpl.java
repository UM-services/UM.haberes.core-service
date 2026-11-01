package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.application.service.ItemBulkDeleter;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.DeleteItemsByPeriodoUseCase;

/**
 * Borra los items de un período en chunks con transacciones acotadas.
 *
 * <p>No lleva {@code @Transactional} a propósito: si el borrado completo quedara en una sola
 * transacción, los chunks no liberarían memoria ni se auditarían por separado.</p>
 */
@Component
@RequiredArgsConstructor
public class DeleteItemsByPeriodoUseCaseImpl implements DeleteItemsByPeriodoUseCase {

    private final ItemBulkDeleter itemBulkDeleter;

    @Override
    public void deleteItemsByPeriodo(Integer anho, Integer mes) {
        itemBulkDeleter.deleteByPeriodo(anho, mes);
    }
}
