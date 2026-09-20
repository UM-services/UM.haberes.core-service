/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import um.haberes.core.model.LectivoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.LectivoException;
import um.haberes.core.repository.JpaLectivoRepository;

/**
 * @author daniel
 *
 */
@Service
public class LectivoService {

	@Autowired
	private JpaLectivoRepository repository;

	public List<LectivoEntity> findAll() {
		return repository.findAll();
	}

	public List<LectivoEntity> findAllReverse() {
		return repository.findAll(Sort.by("lectivoId").descending());
	}

	public LectivoEntity findByLectivoId(Integer lectivoId) {
		return repository.findByLectivoId(lectivoId).orElseThrow(() -> new LectivoException(lectivoId));
	}

	public List<LectivoEntity> saveAll(List<LectivoEntity> lectivos) {
		return repository.saveAll(lectivos);
	}

}
