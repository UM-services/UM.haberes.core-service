package um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;

public interface ItemRepository {

    Item save(Item item);

    List<Item> saveAll(List<Item> items);

    Optional<Item> findByItemId(Long itemId);

    Optional<Item> findByLegajoIdAndAnhoAndMesAndCodigoId(Long legajoId, Integer anho, Integer mes, Integer codigoId);

    List<Item> findAllByCodigoIdAndAnhoAndMes(Integer codigoId, Integer anho, Integer mes);

    List<Item> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    List<Item> findAllByAnhoAndMes(Integer anho, Integer mes, Integer limit);

    List<Item> findAllByAnhoAndMesAndLegajoId(Integer anho, Integer mes, Long legajoId, Integer limit);

    List<Item> findAllByAnhoAndMesAndCodigoIdAndImporteGreaterThan(Integer anho, Integer mes, Integer codigoId,
            BigDecimal importe);

    List<Item> findAllByLegajoIdAndAnhoAndMesAndCodigoIdIn(Long legajoId, Integer anho, Integer mes,
            List<Integer> codigoIds);

    void deleteAllByAnhoAndMes(Integer anho, Integer mes);

    void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    void deleteAllByLegajoIdAndAnhoAndMesAndImporteAndCodigoIdLessThan(Long legajoId, Integer anho, Integer mes,
            BigDecimal importe, Integer codigoId);

    void deleteAllByLegajoIdAndAnhoAndMesAndImporteAndCodigoIdGreaterThan(Long legajoId, Integer anho, Integer mes,
            BigDecimal importe, Integer codigoId);
}
