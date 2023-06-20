/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import um.haberes.rest.kotlin.model.Lectivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.LectivoException;
import um.haberes.rest.repository.ILectivoRepository;

/**
 * @author daniel
 *
 */
@Service
public class LectivoService {

	@Autowired
	private ILectivoRepository repository;

	public List<Lectivo> findAll() {
		return repository.findAll();
	}

	public List<Lectivo> findAllReverse() {
		return repository.findAll(Sort.by("lectivoId").descending());
	}

	public Lectivo findByLectivoId(Integer lectivoId) {
		return repository.findByLectivoId(lectivoId).orElseThrow(() -> new LectivoException(lectivoId));
	}

	public List<Lectivo> saveAll(List<Lectivo> lectivos) {
		return repository.saveAll(lectivos);
	}

}
