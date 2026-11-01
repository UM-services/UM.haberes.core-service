package um.haberes.core.hexagonal.liquidaciones.item.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.extern.slf4j.Slf4j;
import um.haberes.core.audit.AuditBulkScope;
import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.audit.BulkContext;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;

/**
 * Borrado de todos los items de un período en chunks, para no cargar decenas de miles de
 * entidades en una sola transacción.
 *
 * <p>Cada chunk es su propia transacción ({@code REQUIRES_NEW}) y su propio
 * {@link AuditBulkScope}: si un chunk falla, lo ya commiteado queda auditado con su resumen y
 * el resto no se ejecuta. Todos los chunks comparten {@code bulkId} para poder agruparlos en
 * la consulta.</p>
 */
@Component
@Slf4j
public class ItemBulkDeleter {

    static final int CHUNK_SIZE = 1000;

    private final ItemRepository itemRepository;
    private final AuditBulkService auditBulk;
    private final TransactionTemplate transactionTemplate;

    public ItemBulkDeleter(ItemRepository itemRepository, AuditBulkService auditBulk,
                           PlatformTransactionManager transactionManager) {
        this.itemRepository = itemRepository;
        this.auditBulk = auditBulk;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    public void deleteByPeriodo(Integer anho, Integer mes) {
        String bulkId = UUID.randomUUID().toString();
        int index = 0;
        int total = 0;
        while (true) {
            int deleted = deleteChunk(anho, mes, bulkId, index);
            if (deleted == 0) {
                break;
            }
            total += deleted;
            index++;
        }
        log.info("borrado de items del período {}-{}: {} filas en {} chunk(s)", anho, mes, total, index);
    }

    private int deleteChunk(Integer anho, Integer mes, String bulkId, int index) {
        Integer deleted = transactionTemplate.execute(status -> {
            // La página se vuelve a pedir desde el principio: cada chunk borra lo anterior,
            // así que el offset siempre es cero.
            List<Item> page = itemRepository.findAllByAnhoAndMes(anho, mes, CHUNK_SIZE);
            if (page.isEmpty()) {
                return 0;
            }
            try (AuditBulkScope ignored = auditBulk.begin(
                    BulkContext.chunk("borrado-periodo-item", anho, mes, bulkId, index, CHUNK_SIZE))) {
                itemRepository.deleteAll(page);
            }
            return page.size();
        });
        return deleted == null ? 0 : deleted;
    }
}
