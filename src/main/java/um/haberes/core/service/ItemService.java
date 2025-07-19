/**
 *
 */
package um.haberes.core.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.ItemException;
import um.haberes.core.kotlin.model.CodigoGrupo;
import um.haberes.core.kotlin.model.Item;
import um.haberes.core.kotlin.model.ItemVersion;
import um.haberes.core.repository.ItemRepository;
import um.haberes.core.repository.ItemVersionRepository;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class ItemService {

    private final ItemRepository repository;

    private final ItemVersionRepository itemVersionRepository;

    private final CodigoGrupoService codigoGrupoService;

    @Autowired
    public ItemService(ItemRepository repository, ItemVersionRepository itemVersionRepository, CodigoGrupoService codigoGrupoService) {
        this.repository = repository;
        this.itemVersionRepository = itemVersionRepository;
        this.codigoGrupoService = codigoGrupoService;
    }

    public List<Item> findAllByCodigo(Integer codigoId, Integer anho, Integer mes) {
        return repository.findAllByCodigoIdAndAnhoAndMes(codigoId, anho, mes);
    }

    public List<Item> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }

    public List<Item> findAllByPeriodo(Integer anho, Integer mes, Integer limit) {
        return repository.findAllByAnhoAndMes(anho, mes, PageRequest.of(0, limit));
    }

    public List<Item> findAllByPeriodoAndLegajo(Integer anho, Integer mes, Long legajoId, Integer limit) {
        return repository.findAllByAnhoAndMesAndLegajoId(anho, mes, legajoId, PageRequest.of(0, limit));
    }

    public List<Item> findAllByNetoPositivo(Integer anho, Integer mes) {
        return repository.findAllByAnhoAndMesAndCodigoIdAndImporteGreaterThan(anho, mes, 99, BigDecimal.ZERO);
    }

    public List<Item> findAllCodigoIdsByLegajo(Long legajoId, Integer anho, Integer mes, List<Integer> codigoIds) {
        return repository.findAllByLegajoIdAndAnhoAndMesAndCodigoIdIn(legajoId, anho, mes, codigoIds);
    }

    public Item findByUnique(Long legajoId, Integer anho, Integer mes, Integer codigoId) {
        return repository.findByLegajoIdAndAnhoAndMesAndCodigoId(legajoId, anho, mes, codigoId)
                .orElseThrow(() -> new ItemException(legajoId, anho, mes, codigoId));
    }

    @Transactional
    public Item add(Item item) {
        repository.save(item);
        ItemVersion itemVersion = new ItemVersion(null, item.getLegajoId(), item.getAnho(), item.getMes(),
                item.getCodigoId(), item.getCodigoNombre(), item.getImporte());
        itemVersionRepository.save(itemVersion);
        return item;
    }

    @Transactional
    public Item update(Item newItem, Long itemId) {
        return repository.findById(itemId).map(item -> {
            item = new Item(itemId, newItem.getLegajoId(), newItem.getAnho(), newItem.getMes(), newItem.getCodigoId(),
                    newItem.getCodigoNombre(), newItem.getImporte(), newItem.getPersona(), newItem.getCodigo());
            ItemVersion itemVersion = new ItemVersion(null, item.getLegajoId(), item.getAnho(), item.getMes(),
                    item.getCodigoId(), item.getCodigoNombre(), item.getImporte());
            itemVersionRepository.save(itemVersion);
            repository.save(item);
            return item;
        }).orElseThrow(() -> new ItemException(itemId));
    }

    @Transactional
    public void deleteByPeriodo(Integer anho, Integer mes) {
        repository.deleteAllByAnhoAndMes(anho, mes);
    }

    @Transactional
    public void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        repository.deleteAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }

    @Transactional
    public void deleteAllByZero(Long legajoId, Integer anho, Integer mes) {
        repository.deleteAllByLegajoIdAndAnhoAndMesAndImporteAndCodigoIdLessThan(legajoId, anho, mes, BigDecimal.ZERO,
                96);
    }

    @Transactional
    public List<Item> saveAll(List<Item> items) {
        List<ItemVersion> itemVersions = new ArrayList<>();
        for (Item item : items) {
            ItemVersion itemVersion = new ItemVersion(null, item.getLegajoId(), item.getAnho(), item.getMes(),
                    item.getCodigoId(), item.getCodigoNombre(), item.getImporte());
            itemVersions.add(itemVersion);
        }
        repository.saveAll(items);
        itemVersionRepository.saveAll(itemVersions);
        return items;
    }

    public Boolean onlyETEC(Long legajoId, Integer anho, Integer mes) {
        AtomicBoolean result = new AtomicBoolean(true);
        List<Integer> codigoIds = codigoGrupoService.findAllByRemunerativo((byte) 1).stream().map(CodigoGrupo::getCodigoId).collect(Collectors.toList());
        log.debug(codigoIds.toString());
        List<Item> items = repository.findAllByLegajoIdAndAnhoAndMesAndCodigoIdIn(legajoId, anho, mes, codigoIds).stream().peek(item -> {
            if (item.getCodigo().getIncluidoEtec() == 0) {
                result.set(false);
            }
        }).collect(Collectors.toList());
        try {
            log.debug("Items -> {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(items));
        } catch (JsonProcessingException e) {
            log.debug("Sin Items");
        }
        return result.get();
    }

}
