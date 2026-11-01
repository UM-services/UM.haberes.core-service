package um.haberes.core.hexagonal.liquidaciones.item.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.SimpleTransactionStatus;
import um.haberes.core.audit.AuditBulkScope;
import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.audit.BulkContext;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemBulkDeleterTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private AuditBulkService auditBulk;

    @Mock
    private AuditBulkScope scope;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Test
    void borraPorChunksConUnaTransaccionYUnScopePorChunk() {
        when(transactionManager.getTransaction(any())).thenReturn(new SimpleTransactionStatus());
        when(auditBulk.begin(any())).thenReturn(scope);
        when(itemRepository.findAllByAnhoAndMes(eq(2026), eq(8), eq(ItemBulkDeleter.CHUNK_SIZE)))
                .thenReturn(List.of(item(1L), item(2L)), List.of(item(3L)), List.of());

        new ItemBulkDeleter(itemRepository, auditBulk, transactionManager).deleteByPeriodo(2026, 8);

        verify(itemRepository, times(2)).deleteAll(anyList());
        verify(transactionManager, times(3)).getTransaction(any());
        verify(transactionManager, times(3)).commit(any());

        ArgumentCaptor<BulkContext> captor = ArgumentCaptor.forClass(BulkContext.class);
        verify(auditBulk, times(2)).begin(captor.capture());
        List<BulkContext> contexts = captor.getAllValues();
        assertThat(contexts).extracting(BulkContext::chunkIndex).containsExactly(0, 1);
        assertThat(contexts).extracting(BulkContext::anho).containsOnly(2026);
        assertThat(contexts).extracting(BulkContext::mes).containsOnly(8);
        assertThat(contexts).extracting(BulkContext::bulkId)
                .containsOnly(contexts.getFirst().bulkId());
    }

    @Test
    void sinFilasNoAbreNingunScope() {
        when(transactionManager.getTransaction(any())).thenReturn(new SimpleTransactionStatus());
        when(itemRepository.findAllByAnhoAndMes(eq(2026), eq(8), eq(ItemBulkDeleter.CHUNK_SIZE)))
                .thenReturn(List.of());

        new ItemBulkDeleter(itemRepository, auditBulk, transactionManager).deleteByPeriodo(2026, 8);

        verify(itemRepository, times(0)).deleteAll(anyList());
        verify(auditBulk, times(0)).begin(any());
    }

    private Item item(Long itemId) {
        return Item.builder()
                .itemId(itemId)
                .legajoId(12345L)
                .anho(2026)
                .mes(8)
                .codigoId(1)
                .build();
    }
}
