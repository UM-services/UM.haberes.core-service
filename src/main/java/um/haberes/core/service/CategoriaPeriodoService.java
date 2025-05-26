/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import um.haberes.core.exception.CategoriaPeriodoException;
import um.haberes.core.kotlin.model.CategoriaPeriodo;
import um.haberes.core.repository.ICategoriaPeriodoRepository;

/**
 * @author daniel
 *
 */
@Service
public class CategoriaPeriodoService {

	private final ICategoriaPeriodoRepository repository;

	public CategoriaPeriodoService(ICategoriaPeriodoRepository repository) {
		this.repository = repository;
	}

	public List<CategoriaPeriodo> findAllByAnhoAndMes(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

	public CategoriaPeriodo findByUnique(Integer categoriaId, Integer anho, Integer mes) {
		return repository.findByCategoriaIdAndAnhoAndMes(categoriaId, anho, mes)
				.orElseThrow(() -> new CategoriaPeriodoException(categoriaId, anho, mes));
	}

	public CategoriaPeriodo add(CategoriaPeriodo categoriaPeriodo) {
		categoriaPeriodo = repository.save(categoriaPeriodo);
		return categoriaPeriodo;
	}

	public CategoriaPeriodo update(CategoriaPeriodo newCategoriaPeriodo, Long categoriaPeriodoId) {
		return repository.findByCategoriaPeriodoId(categoriaPeriodoId).map(categoriaPeriodo -> {
			categoriaPeriodo = new CategoriaPeriodo(categoriaPeriodoId, newCategoriaPeriodo.getCategoriaId(),
					newCategoriaPeriodo.getAnho(), newCategoriaPeriodo.getMes(), newCategoriaPeriodo.getNombre(),
					newCategoriaPeriodo.getBasico(), newCategoriaPeriodo.getDocente(),
					newCategoriaPeriodo.getNoDocente(), newCategoriaPeriodo.getLiquidaPorHora(), newCategoriaPeriodo.getEstadoDocente());
			categoriaPeriodo = repository.save(categoriaPeriodo);
			return categoriaPeriodo;
		}).orElseThrow(() -> new CategoriaPeriodoException(categoriaPeriodoId));
	}

	@Transactional
	public List<CategoriaPeriodo> saveAll(List<CategoriaPeriodo> categoriaPeriodos) {
		categoriaPeriodos = repository.saveAll(categoriaPeriodos);
		return categoriaPeriodos;
	}

}
