/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.SituacionException;
import um.haberes.core.kotlin.model.Situacion;
import um.haberes.core.repository.SituacionRepository;

/**
 * @author daniel
 *
 */
@Service
@RequiredArgsConstructor
public class SituacionService {

	private final SituacionRepository repository;

	public List<Situacion> findAll() {
		return repository.findAll();
	}

	public Situacion findBySituacionId(Integer situacionId) {
		return repository.findById(situacionId).orElseThrow(() -> new SituacionException(situacionId));
	}
}
