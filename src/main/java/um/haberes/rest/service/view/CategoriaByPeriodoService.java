/**
 * 
 */
package um.haberes.rest.service.view;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.kotlin.model.view.CategoriaByPeriodo;
import um.haberes.rest.repository.ICategoriaRepository;
import um.haberes.rest.repository.view.ICategoriaByPeriodoRepository;

/**
 * @author daniel
 *
 */
@Service
public class CategoriaByPeriodoService {

	@Autowired
	private ICategoriaByPeriodoRepository repository;

	@Autowired
	private ICategoriaRepository categoriaRepository;

	public List<CategoriaByPeriodo> findAllNoDocente(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndCategoriaIdIn(anho, mes,
				categoriaRepository.findAllByNoDocente((byte) 1, Sort.by("categoriaId").ascending()).stream()
						.map(categoria -> categoria.getCategoriaId()).collect(Collectors.toList()));
	}

}
