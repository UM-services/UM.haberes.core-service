/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.SituacionException;
import um.haberes.core.kotlin.model.Situacion;
import um.haberes.core.repository.SituacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class SituacionService {
	@Autowired
	private SituacionRepository repository;

	public List<Situacion> findAll() {
		return repository.findAll();
	}

	public Situacion findBySituacionID(Integer situacionID) {
		return repository.findById(situacionID).orElseThrow(() -> new SituacionException(situacionID));
	}
}
