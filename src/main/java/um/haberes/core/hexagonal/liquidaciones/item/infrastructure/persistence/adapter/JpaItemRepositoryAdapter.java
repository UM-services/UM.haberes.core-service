package um.haberes.core.hexagonal.liquidaciones.item.infrastructure.persistence.adapter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;
import um.haberes.core.hexagonal.liquidaciones.item.infrastructure.persistence.entity.ItemEntity;
import um.haberes.core.hexagonal.liquidaciones.item.infrastructure.persistence.mapper.ItemMapper;
import um.haberes.core.hexagonal.liquidaciones.item.infrastructure.persistence.repository.JpaItemRepository;

@Component
@RequiredArgsConstructor
public class JpaItemRepositoryAdapter implements ItemRepository {

    private final JpaItemRepository jpaItemRepository;
    private final ItemMapper itemMapper;

    @Override
    public Item save(Item item) {
        return itemMapper.toDomain(jpaItemRepository.save(itemMapper.toEntity(item)));
    }

    @Override
    public List<Item> saveAll(List<Item> items) {
        List<ItemEntity> entities = items.stream()
                .map(itemMapper::toEntity)
                .collect(Collectors.toList());
        return toDomainList(jpaItemRepository.saveAll(entities));
    }

    @Override
    public Optional<Item> findByItemId(Long itemId) {
        return jpaItemRepository.findById(itemId).map(itemMapper::toDomain);
    }

    @Override
    public Optional<Item> findByLegajoIdAndAnhoAndMesAndCodigoId(Long legajoId, Integer anho, Integer mes,
            Integer codigoId) {
        return jpaItemRepository.findByLegajoIdAndAnhoAndMesAndCodigoId(legajoId, anho, mes, codigoId)
                .map(itemMapper::toDomain);
    }

    @Override
    public List<Item> findAllByCodigoIdAndAnhoAndMes(Integer codigoId, Integer anho, Integer mes) {
        return toDomainList(jpaItemRepository.findAllByCodigoIdAndAnhoAndMes(codigoId, anho, mes));
    }

    @Override
    public List<Item> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        return toDomainList(jpaItemRepository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes));
    }

    @Override
    public List<Item> findAllByAnhoAndMes(Integer anho, Integer mes, Integer limit) {
        return toDomainList(jpaItemRepository.findAllByAnhoAndMes(anho, mes, PageRequest.of(0, limit)));
    }

    @Override
    public List<Item> findAllByAnhoAndMesAndLegajoId(Integer anho, Integer mes, Long legajoId, Integer limit) {
        return toDomainList(jpaItemRepository.findAllByAnhoAndMesAndLegajoId(anho, mes, legajoId,
                PageRequest.of(0, limit)));
    }

    @Override
    public List<Item> findAllByAnhoAndMesAndCodigoIdAndImporteGreaterThan(Integer anho, Integer mes, Integer codigoId,
            BigDecimal importe) {
        return toDomainList(
                jpaItemRepository.findAllByAnhoAndMesAndCodigoIdAndImporteGreaterThan(anho, mes, codigoId, importe));
    }

    @Override
    public List<Item> findAllByLegajoIdAndAnhoAndMesAndCodigoIdIn(Long legajoId, Integer anho, Integer mes,
            List<Integer> codigoIds) {
        return toDomainList(
                jpaItemRepository.findAllByLegajoIdAndAnhoAndMesAndCodigoIdIn(legajoId, anho, mes, codigoIds));
    }

    @Override
    public void deleteAllByAnhoAndMes(Integer anho, Integer mes) {
        jpaItemRepository.deleteAllByAnhoAndMes(anho, mes);
    }

    @Override
    public void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        jpaItemRepository.deleteAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }

    @Override
    public void deleteAllByLegajoIdAndAnhoAndMesAndImporteAndCodigoIdLessThan(Long legajoId, Integer anho,
            Integer mes, BigDecimal importe, Integer codigoId) {
        jpaItemRepository.deleteAllByLegajoIdAndAnhoAndMesAndImporteAndCodigoIdLessThan(legajoId, anho, mes, importe,
                codigoId);
    }

    @Override
    public void deleteAllByLegajoIdAndAnhoAndMesAndImporteAndCodigoIdGreaterThan(Long legajoId, Integer anho,
            Integer mes, BigDecimal importe, Integer codigoId) {
        jpaItemRepository.deleteAllByLegajoIdAndAnhoAndMesAndImporteAndCodigoIdGreaterThan(legajoId, anho, mes,
                importe, codigoId);
    }

    private List<Item> toDomainList(List<ItemEntity> entities) {
        return entities.stream()
                .map(itemMapper::toDomain)
                .collect(Collectors.toList());
    }
}
