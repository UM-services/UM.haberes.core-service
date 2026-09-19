/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import um.haberes.core.exception.CategoriaPeriodoException;
import um.haberes.core.model.CategoriaPeriodoEntity;
import java.util.Set;
import um.haberes.core.repository.JpaCategoriaPeriodoRepository;

/**
 * @author daniel
 *
 */
@Service
public class CategoriaPeriodoService {

	private final JpaCategoriaPeriodoRepository repository;

	public CategoriaPeriodoService(JpaCategoriaPeriodoRepository repository) {
		this.repository = repository;
	}

	public List<CategoriaPeriodoEntity> findAllByAnhoAndMes(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

	public List<CategoriaPeriodoEntity> findAllByCategoriaIdsAndPeriodo(Set<Integer> categoriaIds, Integer anho, Integer mes) {
		return repository.findAllByCategoriaIdInAndAnhoAndMes(categoriaIds, anho, mes);
	}

	public CategoriaPeriodoEntity findByUnique(Integer categoriaId, Integer anho, Integer mes) {
		return repository.findByCategoriaIdAndAnhoAndMes(categoriaId, anho, mes)
				.orElseThrow(() -> new CategoriaPeriodoException(categoriaId, anho, mes));
	}

	public CategoriaPeriodoEntity add(CategoriaPeriodoEntity categoriaPeriodo) {
		categoriaPeriodo = repository.save(categoriaPeriodo);
		return categoriaPeriodo;
	}

	public CategoriaPeriodoEntity update(CategoriaPeriodoEntity newCategoriaPeriodo, Long categoriaPeriodoId) {
		return repository.findByCategoriaPeriodoId(categoriaPeriodoId).map(categoriaPeriodo -> {
			categoriaPeriodo = new CategoriaPeriodoEntity(categoriaPeriodoId, newCategoriaPeriodo.getCategoriaId(),
					newCategoriaPeriodo.getAnho(), newCategoriaPeriodo.getMes(), newCategoriaPeriodo.getNombre(),
					newCategoriaPeriodo.getBasico(), newCategoriaPeriodo.getDocente(),
					newCategoriaPeriodo.getNoDocente(), newCategoriaPeriodo.getLiquidaPorHora(), newCategoriaPeriodo.getEstadoDocente());
			categoriaPeriodo = repository.save(categoriaPeriodo);
			return categoriaPeriodo;
		}).orElseThrow(() -> new CategoriaPeriodoException(categoriaPeriodoId));
	}

	@Transactional
	public List<CategoriaPeriodoEntity> saveAll(List<CategoriaPeriodoEntity> categoriaPeriodos) {
		categoriaPeriodos = repository.saveAll(categoriaPeriodos);
		return categoriaPeriodos;
	}

}
