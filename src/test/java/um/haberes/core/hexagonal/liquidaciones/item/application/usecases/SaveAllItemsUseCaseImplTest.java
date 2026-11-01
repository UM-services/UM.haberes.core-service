package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import um.haberes.core.audit.AuditBulkScope;
import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.audit.BulkContext;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemVersionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveAllItemsUseCaseImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemVersionRepository itemVersionRepository;

    @Mock
    private AuditBulkService auditBulk;

    @Mock
    private AuditBulkScope scope;

    @Test
    void abreUnScopeConPeriodoYLegajoCuandoElLoteEsDeUnLegajo() {
        when(auditBulk.begin(any())).thenReturn(scope);

        new SaveAllItemsUseCaseImpl(itemRepository, itemVersionRepository, auditBulk)
                .saveAllItems(List.of(item(1L), item(2L)));

        ArgumentCaptor<BulkContext> captor = ArgumentCaptor.forClass(BulkContext.class);
        verify(auditBulk).begin(captor.capture());
        BulkContext context = captor.getValue();
        assertThat(context.anho()).isEqualTo(2026);
        assertThat(context.mes()).isEqualTo(8);
        assertThat(context.legajoId()).isEqualTo(12345L);

        verify(itemRepository).saveAll(any());
        verify(itemVersionRepository).saveAll(any());
        verify(scope).close();
    }

    @Test
    void noAtribuyeLegajoCuandoElLoteEsMixto() {
        when(auditBulk.begin(any())).thenReturn(scope);

        new SaveAllItemsUseCaseImpl(itemRepository, itemVersionRepository, auditBulk)
                .saveAllItems(List.of(item(1L), itemDeOtroLegajo(2L)));

        ArgumentCaptor<BulkContext> captor = ArgumentCaptor.forClass(BulkContext.class);
        verify(auditBulk).begin(captor.capture());
        BulkContext context = captor.getValue();
        assertThat(context.legajoId()).isNull();
        // El período sí es homogéneo, así que se conserva.
        assertThat(context.anho()).isEqualTo(2026);
        assertThat(context.mes()).isEqualTo(8);
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

    private Item itemDeOtroLegajo(Long itemId) {
        return Item.builder()
                .itemId(itemId)
                .legajoId(77777L)
                .anho(2026)
                .mes(8)
                .codigoId(1)
                .build();
    }
}
