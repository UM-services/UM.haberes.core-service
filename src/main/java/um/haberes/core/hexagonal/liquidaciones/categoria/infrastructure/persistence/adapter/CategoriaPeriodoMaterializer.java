package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.adapter;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import um.haberes.core.model.CategoriaPeriodoEntity;
import um.haberes.core.repository.JpaCategoriaPeriodoRepository;

/**
 * Registra en {@code categoria_periodo} los valores ETEC que la lectura tomó de la
 * plantilla {@code categoria} porque la fila del período no existía o todavía no tenía
 * puntaje.
 *
 * <p>Es un guardado best-effort: la lectura ya resolvió el valor en memoria, así que si
 * la persistencia falla (por ejemplo, dos requests creando la misma fila a la vez) el
 * catálogo sigue respondiendo y el próximo request vuelve a intentarlo.</p>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CategoriaPeriodoMaterializer {

    private final JpaCategoriaPeriodoRepository jpaCategoriaPeriodoRepository;
    private final PlatformTransactionManager transactionManager;

    public void guardar(List<CategoriaPeriodoEntity> filas) {
        if (filas.isEmpty() || TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return;
        }
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        try {
            template.executeWithoutResult(status -> jpaCategoriaPeriodoRepository.saveAll(filas));
        } catch (RuntimeException e) {
            log.warn("No se pudo registrar categoria_periodo desde la plantilla: {}", e.getMessage());
        }
    }
}
