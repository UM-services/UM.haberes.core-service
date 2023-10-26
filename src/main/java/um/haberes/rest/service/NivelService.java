/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.kotlin.model.Nivel;
import um.haberes.rest.repository.INivelRepository;

/**
 * @author daniel
 *
 */
@Service
public class NivelService {
	
	@Autowired
	private INivelRepository repository;

	public List<Nivel> findAll() {
		return repository.findAll();
	}
}
