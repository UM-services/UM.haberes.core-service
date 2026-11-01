package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.CheckItemsOnlyEtecUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.CodigoGrupoRepository;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;

@Component
@RequiredArgsConstructor
public class CheckItemsOnlyEtecUseCaseImpl implements CheckItemsOnlyEtecUseCase {

    private static final Byte REMUNERATIVO = (byte) 1;
    private static final Byte INCLUIDO_ETEC_NO = (byte) 0;

    private final ItemRepository itemRepository;
    private final CodigoGrupoRepository codigoGrupoRepository;

    @Override
    public boolean checkItemsOnlyEtec(Long legajoId, Integer anho, Integer mes) {
        List<Integer> remunerativoCodigoIds = codigoGrupoRepository.findCodigoIdsByRemunerativo(REMUNERATIVO);
        List<Item> items = itemRepository.findAllByLegajoIdAndAnhoAndMesAndCodigoIdIn(legajoId, anho, mes,
                remunerativoCodigoIds);
        return items.stream().noneMatch(item -> INCLUIDO_ETEC_NO.equals(item.getCodigoIncluidoEtec()));
    }
}
