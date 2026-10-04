package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.DeleteZeroItemsByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;

@Component
@RequiredArgsConstructor
public class DeleteZeroItemsByLegajoUseCaseImpl implements DeleteZeroItemsByLegajoUseCase {

    private static final BigDecimal ZERO_IMPORTE = BigDecimal.ZERO;
    private static final Integer MIN_CODIGO_ID_EXCLUIDO = 96;
    private static final Integer MAX_CODIGO_ID_EXCLUIDO = 100;

    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public void deleteZeroItemsByLegajo(Long legajoId, Integer anho, Integer mes) {
        itemRepository.deleteAllByLegajoIdAndAnhoAndMesAndImporteAndCodigoIdLessThan(legajoId, anho, mes,
                ZERO_IMPORTE, MIN_CODIGO_ID_EXCLUIDO);
        itemRepository.deleteAllByLegajoIdAndAnhoAndMesAndImporteAndCodigoIdGreaterThan(legajoId, anho, mes,
                ZERO_IMPORTE, MAX_CODIGO_ID_EXCLUIDO);
    }
}
