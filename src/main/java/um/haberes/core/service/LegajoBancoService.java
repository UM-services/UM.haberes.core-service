/**
 * 
 */
package um.haberes.core.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.LegajoBancoException;
import um.haberes.core.hexagonal.liquidaciones.item.application.service.ItemService;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.service.LiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.service.NovedadService;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.model.LegajoBancoEntity;
import um.haberes.core.repository.JpaLegajoBancoRepository;
import um.haberes.core.util.Jsonifier;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LegajoBancoService {

	private final JpaLegajoBancoRepository repository;
	private final ItemService itemService;
	private final LiquidacionService liquidacionService;
    private final NovedadService novedadService;

    public List<LegajoBancoEntity> findAllSantander(String salida, Integer anho, Integer mes, Integer dependenciaId) {
		List<Long> legajoIds = liquidacionService
				.getLiquidacionesByLegajoIdsAndPeriodo(itemService.getItemsByNetoPositivo(anho, mes).stream()
						.map(Item::getLegajoId).toList(), anho, mes)
				.stream().map(Liquidacion::getLegajoId).toList();
		List<LegajoBancoEntity> legajoBancos = repository.findAllByLegajoIdInAndAnhoAndMesAndCbuLike(legajoIds, anho, mes,
				"072%");
		if (salida.equals("T")) {
			if (dependenciaId == 0) {
				return legajoBancos;
			}
			return legajoBancos.stream()
					.filter(legajoBanco -> Objects.equals(Objects.requireNonNull(legajoBanco.getLiquidacion()).getDependenciaId(), dependenciaId))
					.toList();
		}
		legajoBancos = legajoBancos.stream()
				.filter(legajoBanco -> Objects.equals(Objects.requireNonNull(legajoBanco.getLiquidacion()).getSalida(), salida))
				.toList();
		if (dependenciaId == 0)
			return legajoBancos;
		return legajoBancos.stream()
				.filter(legajoBanco -> Objects.equals(Objects.requireNonNull(legajoBanco.getLiquidacion()).getDependenciaId(), dependenciaId))
				.toList();
	}

    public List<LegajoBancoEntity> findAllSantanderConCodigo(String salida, Integer anho, Integer mes, Integer dependenciaId, Integer codigoId) {
        List<Long> legajoIds = novedadService.findAllByCodigo(codigoId, anho, mes)
                .stream()
                .map(Novedad::getLegajoId).toList();
        List<LegajoBancoEntity> legajoBancos = repository.findAllByLegajoIdInAndAnhoAndMesAndCbuLike(legajoIds, anho, mes,
                "072%");
        if (salida.equals("T")) {
            if (dependenciaId == 0) {
                return legajoBancos;
            }
            return legajoBancos.stream()
                    .filter(legajoBanco -> Objects.equals(Objects.requireNonNull(legajoBanco.getLiquidacion()).getDependenciaId(), dependenciaId))
                    .toList();
        }
        legajoBancos = legajoBancos.stream()
                .filter(legajoBanco -> Objects.equals(Objects.requireNonNull(legajoBanco.getLiquidacion()).getSalida(), salida))
                .toList();
        if (dependenciaId == 0)
            return legajoBancos;
        return legajoBancos.stream()
                .filter(legajoBanco -> Objects.equals(Objects.requireNonNull(legajoBanco.getLiquidacion()).getDependenciaId(), dependenciaId))
                .toList();
    }

    public List<LegajoBancoEntity> findAllOtrosBancos(String salida, Integer anho, Integer mes, Integer dependenciaId) {
		List<Long> legajoIds = liquidacionService
				.getLiquidacionesByLegajoIdsAndPeriodo(itemService.getItemsByNetoPositivo(anho, mes).stream()
						.map(Item::getLegajoId).collect(Collectors.toList()), anho, mes)
				.stream().map(Liquidacion::getLegajoId).collect(Collectors.toList());
		List<LegajoBancoEntity> legajoBancos = repository.findAllByLegajoIdInAndAnhoAndMesAndCbuNotLike(legajoIds, anho, mes,
				"072%");
		if (salida.equals("T")) {
			if (dependenciaId == 0) {
				return legajoBancos;
			}
			return legajoBancos.stream()
					.filter(legajoBanco -> Objects.equals(Objects.requireNonNull(legajoBanco.getLiquidacion()).getDependenciaId(), dependenciaId))
					.toList();
		}
		legajoBancos = legajoBancos.stream()
				.filter(legajoBanco -> Objects.equals(Objects.requireNonNull(legajoBanco.getLiquidacion()).getSalida(), salida))
				.toList();
		if (dependenciaId == 0)
			return legajoBancos;
		return legajoBancos.stream()
				.filter(legajoBanco -> Objects.equals(Objects.requireNonNull(legajoBanco.getLiquidacion()).getDependenciaId(), dependenciaId))
				.toList();
	}

    public List<LegajoBancoEntity> findAllOtrosBancosConCodigo(String salida, Integer anho, Integer mes, Integer dependenciaId, Integer codigoId) {
        List<Long> legajoIds = novedadService.findAllByCodigo(codigoId, anho, mes)
                .stream()
                .map(Novedad::getLegajoId).toList();
        List<LegajoBancoEntity> legajoBancos = repository.findAllByLegajoIdInAndAnhoAndMesAndCbuNotLike(legajoIds, anho, mes,
                "072%");
        if (salida.equals("T")) {
            if (dependenciaId == 0) {
                return legajoBancos;
            }
            return legajoBancos.stream()
                    .filter(legajoBanco -> Objects.equals(Objects.requireNonNull(legajoBanco.getLiquidacion()).getDependenciaId(), dependenciaId))
                    .toList();
        }
        legajoBancos = legajoBancos.stream()
                .filter(legajoBanco -> Objects.equals(Objects.requireNonNull(legajoBanco.getLiquidacion()).getSalida(), salida))
                .toList();
        if (dependenciaId == 0)
            return legajoBancos;
        return legajoBancos.stream()
                .filter(legajoBanco -> Objects.equals(Objects.requireNonNull(legajoBanco.getLiquidacion()).getDependenciaId(), dependenciaId))
                .toList();
    }

    public List<LegajoBancoEntity> findAllByLegajoId(Long legajoId) {
		return repository.findAllByLegajoId(legajoId,
				Sort.by("anho").descending().and(Sort.by("mes").descending().and(Sort.by("cbu").ascending())));
	}

	public List<LegajoBancoEntity> findAllPeriodoSantander(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndCbuLike(legajoId, anho, mes, "072%");
	}

	public List<LegajoBancoEntity> findAllPeriodoOtrosBancos(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndCbuNotLike(legajoId, anho, mes, "072%");
	}

	public List<LegajoBancoEntity> findAllPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

	public List<LegajoBancoEntity> findAllByLegajoPeriodo(Long legajoId, Integer anho, Integer mes) {
        log.debug("Processing LegajoBancoService.findAllByLegajoIdAndAnhoAndMes with legajoId: {}, anho: {}, mes: {}", legajoId, anho, mes);
        var legajoBancoList = repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
        log.debug("LegajoBancoEntity[] -> {}", Jsonifier.builder(legajoBancoList).build());
		return legajoBancoList;
	}

	public LegajoBancoEntity findLegajoCbuPrincipal(Long legajoId, Integer anho, Integer mes) {
		return repository.findByLegajoIdAndAnhoAndMesAndResto(legajoId, anho, mes, (byte) 1)
				.orElseThrow(() -> new LegajoBancoException(legajoId, anho, mes));
	}

	public LegajoBancoEntity findByLegajoBancoId(Long legajoBancoId) {
		return repository.findByLegajoBancoId(legajoBancoId)
				.orElseThrow(() -> new LegajoBancoException(legajoBancoId));
	}

	public LegajoBancoEntity findByUnique(Long legajoId, Integer anho, Integer mes, String cbu) {
		return repository.findByLegajoIdAndAnhoAndMesAndCbu(legajoId, anho, mes, cbu)
				.orElseThrow(() -> new LegajoBancoException(legajoId, anho, mes, cbu));
	}

	public LegajoBancoEntity findLastByLegajoId(Long legajoId) {
		return repository.findTopByLegajoIdOrderByAnhoDescMesDesc(legajoId)
				.orElseThrow(() -> new LegajoBancoException(legajoId));
	}

	@Transactional
	public void deleteAllByPeriodo(Integer anho, Integer mes) {
		repository.deleteAllByAnhoAndMes(anho, mes);
	}

	public LegajoBancoEntity add(LegajoBancoEntity legajoBanco) {
		repository.save(legajoBanco);
		return legajoBanco;
	}

	public LegajoBancoEntity update(LegajoBancoEntity newLegajoBanco, Long legajoBancoId) {
		return repository.findByLegajoBancoId(legajoBancoId).map(legajoBanco -> {
			legajoBanco = new LegajoBancoEntity(newLegajoBanco.getLegajoBancoId(), newLegajoBanco.getLegajoId(),
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
