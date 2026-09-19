package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.DeleteItemsByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;

@Component
@RequiredArgsConstructor
public class DeleteItemsByLegajoUseCaseImpl implements DeleteItemsByLegajoUseCase {

    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public void deleteItemsByLegajo(Long legajoId, Integer anho, Integer mes) {
        itemRepository.deleteAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }
}
