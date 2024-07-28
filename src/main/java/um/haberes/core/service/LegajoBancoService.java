/**
 * 
 */
package um.haberes.core.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.LegajoBancoException;
import um.haberes.core.kotlin.model.LegajoBanco;
import um.haberes.core.repository.ILegajoBancoRepository;

/**
 * @author daniel
 *
 */
@Service
public class LegajoBancoService {

	@Autowired
	private ILegajoBancoRepository repository;

	@Autowired
	private ItemService itemService;

	@Autowired
	private LiquidacionService liquidacionService;

	public List<LegajoBanco> findAllSantander(String salida, Integer anho, Integer mes, Integer dependenciaId) {
		List<Long> legajoIds = liquidacionService
				.findAllByLegajoIdInAndAnhoAndMes(itemService.findAllByNetoPositivo(anho, mes).stream()
						.map(item -> item.getLegajoId()).collect(Collectors.toList()), anho, mes)
				.stream().map(liquidacion -> liquidacion.getLegajoId()).collect(Collectors.toList());
		List<LegajoBanco> legajoBancos = repository.findAllByLegajoIdInAndAnhoAndMesAndCbuLike(legajoIds, anho, mes,
				"072%");
		if (salida.equals("T")) {
			if (dependenciaId == 0) {
				return legajoBancos;
			}
			return legajoBancos.stream()
					.filter(legajoBanco -> legajoBanco.getLiquidacion().getDependenciaId() == dependenciaId)
					.collect(Collectors.toList());
		}
		legajoBancos = legajoBancos.stream()
				.filter(legajoBanco -> legajoBanco.getLiquidacion().getSalida().equals(salida))
				.collect(Collectors.toList());
		if (dependenciaId == 0)
			return legajoBancos;
		return legajoBancos.stream()
				.filter(legajoBanco -> legajoBanco.getLiquidacion().getDependenciaId() == dependenciaId)
				.collect(Collectors.toList());
	}

	public List<LegajoBanco> findAllOtrosBancos(String salida, Integer anho, Integer mes, Integer dependenciaId) {
		List<Long> legajoIds = liquidacionService
				.findAllByLegajoIdInAndAnhoAndMes(itemService.findAllByNetoPositivo(anho, mes).stream()
						.map(item -> item.getLegajoId()).collect(Collectors.toList()), anho, mes)
				.stream().map(liquidacion -> liquidacion.getLegajoId()).collect(Collectors.toList());
		List<LegajoBanco> legajoBancos = repository.findAllByLegajoIdInAndAnhoAndMesAndCbuNotLike(legajoIds, anho, mes,
				"072%");
		if (salida.equals("T")) {
			if (dependenciaId == 0) {
				return legajoBancos;
			}
			return legajoBancos.stream()
					.filter(legajoBanco -> legajoBanco.getLiquidacion().getDependenciaId() == dependenciaId)
					.collect(Collectors.toList());
		}
		legajoBancos = legajoBancos.stream()
				.filter(legajoBanco -> legajoBanco.getLiquidacion().getSalida().equals(salida))
				.collect(Collectors.toList());
		if (dependenciaId == 0)
			return legajoBancos;
		return legajoBancos.stream()
				.filter(legajoBanco -> legajoBanco.getLiquidacion().getDependenciaId() == dependenciaId)
				.collect(Collectors.toList());
	}

	public List<LegajoBanco> findAllByLegajoId(Long legajoId) {
		return repository.findAllByLegajoId(legajoId,
				Sort.by("anho").descending().and(Sort.by("mes").descending().and(Sort.by("cbu").ascending())));
	}

	public List<LegajoBanco> findAllPeriodoSantander(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndCbuLike(legajoId, anho, mes, "072%");
	}

	public List<LegajoBanco> findAllPeriodoOtrosBancos(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndCbuNotLike(legajoId, anho, mes, "072%");
	}

	public List<LegajoBanco> findAllPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

	public List<LegajoBanco> findAllByLegajoPeriodo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	public LegajoBanco findLegajoCbuPrincipal(Long legajoId, Integer anho, Integer mes) {
		return repository.findByLegajoIdAndAnhoAndMesAndResto(legajoId, anho, mes, (byte) 1)
				.orElseThrow(() -> new LegajoBancoException(legajoId, anho, mes));
	}

	public LegajoBanco findByLegajoBancoId(Long legajoBancoId) {
		return repository.findByLegajoBancoId(legajoBancoId)
				.orElseThrow(() -> new LegajoBancoException(legajoBancoId));
	}

	public LegajoBanco findByUnique(Long legajoId, Integer anho, Integer mes, String cbu) {
		return repository.findByLegajoIdAndAnhoAndMesAndCbu(legajoId, anho, mes, cbu)
				.orElseThrow(() -> new LegajoBancoException(legajoId, anho, mes, cbu));
	}

	public LegajoBanco findLastByLegajoId(Long legajoId) {
		return repository.findTopByLegajoIdOrderByAnhoDescMesDesc(legajoId)
				.orElseThrow(() -> new LegajoBancoException(legajoId));
	}

	@Transactional
	public void deleteAllByPeriodo(Integer anho, Integer mes) {
		repository.deleteAllByAnhoAndMes(anho, mes);
	}

	public LegajoBanco add(LegajoBanco legajoBanco) {
		repository.save(legajoBanco);
		return legajoBanco;
	}

	public LegajoBanco update(LegajoBanco newLegajoBanco, Long legajoBancoId) {
		return repository.findByLegajoBancoId(legajoBancoId).map(legajoBanco -> {
			legajoBanco = new LegajoBanco(newLegajoBanco.getLegajoBancoId(), newLegajoBanco.getLegajoId(),
					newLegajoBanco.getAnho(), newLegajoBanco.getMes(), newLegajoBanco.getCbu(),
					newLegajoBanco.getFijo(), newLegajoBanco.getPorcentaje(), newLegajoBanco.getResto(),
					newLegajoBanco.getAcreditado(), newLegajoBanco.getPersona(), newLegajoBanco.getLiquidacion());
			repository.save(legajoBanco);
			return legajoBanco;
		}).orElseThrow(() -> new LegajoBancoException(legajoBancoId));
	}

	@Transactional
	public void deleteByLegajobancoId(Long legajoBancoId) {
		repository.deleteByLegajoBancoId(legajoBancoId);
	}

}
