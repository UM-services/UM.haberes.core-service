/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.SituacionException;
import um.haberes.core.model.SituacionEntity;
import um.haberes.core.repository.JpaSituacionRepository;

/**
 * @author daniel
 *
 */
@Service
@RequiredArgsConstructor
public class SituacionService {

	private final JpaSituacionRepository repository;

	public List<SituacionEntity> findAll() {
		return repository.findAll();
	}

	public SituacionEntity findBySituacionId(Integer situacionId) {
		return repository.findById(situacionId).orElseThrow(() -> new SituacionException(situacionId));
	}
}
