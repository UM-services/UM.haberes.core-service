/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.SituacionNotFoundException;
import ar.edu.um.haberes.rest.model.Situacion;
import ar.edu.um.haberes.rest.repository.ISituacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class SituacionService {
	@Autowired
	private ISituacionRepository repository;

	public List<Situacion> findAll() {
		return repository.findAll();
	}

	public Situacion findBySituacionID(Integer situacionID) {
		return repository.findById(situacionID).orElseThrow(() -> new SituacionNotFoundException(situacionID));
	}
}
