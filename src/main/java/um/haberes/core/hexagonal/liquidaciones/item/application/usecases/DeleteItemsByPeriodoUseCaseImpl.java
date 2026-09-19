package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.DeleteItemsByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;

@Component
@RequiredArgsConstructor
public class DeleteItemsByPeriodoUseCaseImpl implements DeleteItemsByPeriodoUseCase {

    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public void deleteItemsByPeriodo(Integer anho, Integer mes) {
        itemRepository.deleteAllByAnhoAndMes(anho, mes);
    }
}
