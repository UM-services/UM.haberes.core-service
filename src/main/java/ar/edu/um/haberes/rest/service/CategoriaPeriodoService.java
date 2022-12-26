/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.CategoriaPeriodoNotFoundException;
import ar.edu.um.haberes.rest.model.CategoriaPeriodo;
import ar.edu.um.haberes.rest.repository.ICategoriaPeriodoRepository;

/**
 * @author daniel
 *
 */
@Service
public class CategoriaPeriodoService {

	@Autowired
	private ICategoriaPeriodoRepository repository;

	public List<CategoriaPeriodo> findAllByAnhoAndMes(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

	public CategoriaPeriodo findByUnique(Integer categoriaId, Integer anho, Integer mes) {
		return repository.findByCategoriaIdAndAnhoAndMes(categoriaId, anho, mes)
				.orElseThrow(() -> new CategoriaPeriodoNotFoundException(categoriaId, anho, mes));
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
					newCategoriaPeriodo.getNoDocente(), newCategoriaPeriodo.getLiquidaPorHora());
			categoriaPeriodo = repository.save(categoriaPeriodo);
			return categoriaPeriodo;
		}).orElseThrow(() -> new CategoriaPeriodoNotFoundException(categoriaPeriodoId));
	}

	@Transactional
	public List<CategoriaPeriodo> saveAll(List<CategoriaPeriodo> categoriaPeriodos) {
		categoriaPeriodos = repository.saveAll(categoriaPeriodos);
		return categoriaPeriodos;
	}

}
