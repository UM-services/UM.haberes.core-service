/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.ItemNotFoundException;
import ar.edu.um.haberes.rest.model.Item;
import ar.edu.um.haberes.rest.model.ItemVersion;
import ar.edu.um.haberes.rest.repository.IItemRepository;
import ar.edu.um.haberes.rest.repository.IItemVersionRepository;

/**
 * @author daniel
 *
 */
@Service
public class ItemService {

	@Autowired
	private IItemRepository repository;

	@Autowired
	private IItemVersionRepository itemVersionRepository;

	@Autowired
	private CodigoGrupoService codigoGrupoService;

	public List<Item> findAllByCodigo(Integer codigoId, Integer anho, Integer mes) {
		return repository.findAllByCodigoIdAndAnhoAndMes(codigoId, anho, mes);
	}

	public List<Item> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	public List<Item> findAllByLegajo(Long legajoId, Integer anho, Integer mes, Integer codigoIdDesde,
			Integer codigoIdHasta) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndCodigoIdBetween(legajoId, anho, mes, codigoIdDesde,
				codigoIdHasta);
	}

	public List<Item> findAllByPeriodo(Integer anho, Integer mes, Integer limit) {
		return repository.findAllByAnhoAndMes(anho, mes, PageRequest.of(0, limit));
	}

	public List<Item> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	public List<Item> findAllByPeriodoAndLegajo(Integer anho, Integer mes, Long legajoId, Integer limit) {
		return repository.findAllByAnhoAndMesAndLegajoId(anho, mes, legajoId, PageRequest.of(0, limit));
	}

	public List<Item> findAllByNetoPositivo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndCodigoIdAndImporteGreaterThan(anho, mes, 99, BigDecimal.ZERO);
	}

	public Item findByUnique(Long legajoId, Integer anho, Integer mes, Integer codigoId) {
		return repository.findByLegajoIdAndAnhoAndMesAndCodigoId(legajoId, anho, mes, codigoId)
				.orElseThrow(() -> new ItemNotFoundException(legajoId, anho, mes, codigoId));
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
		}).orElseThrow(() -> new ItemNotFoundException(itemId));
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
		List<ItemVersion> itemVersions = new ArrayList<ItemVersion>();
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
		List<Item> items = repository.findAllByLegajoIdAndAnhoAndMesAndCodigoIdIn(legajoId, anho, mes,
				codigoGrupoService.findAllByRemunerativo((byte) 1).stream().map(codigo -> codigo.getCodigoId())
						.collect(Collectors.toList()));
		if (items.size() == 1) {
			Item item = items.get(0);
			if (item.getCodigoId() == 29)
				return true;
		}
		return false;
	}

}
