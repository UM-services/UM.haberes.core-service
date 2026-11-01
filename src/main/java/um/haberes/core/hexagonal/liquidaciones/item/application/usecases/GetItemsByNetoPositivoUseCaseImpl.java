package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.GetItemsByNetoPositivoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;

@Component
@RequiredArgsConstructor
public class GetItemsByNetoPositivoUseCaseImpl implements GetItemsByNetoPositivoUseCase {

    private static final Integer NETO_CODIGO_ID = 99;

    private final ItemRepository itemRepository;

    @Override
    public List<Item> getItemsByNetoPositivo(Integer anho, Integer mes) {
        return itemRepository.findAllByAnhoAndMesAndCodigoIdAndImporteGreaterThan(anho, mes, NETO_CODIGO_ID,
                BigDecimal.ZERO);
    }
}
