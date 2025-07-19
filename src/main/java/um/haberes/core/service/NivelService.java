/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.Nivel;
import um.haberes.core.repository.NivelRepository;

/**
 * @author daniel
 *
 */
@Service
public class NivelService {
	
	@Autowired
	private NivelRepository repository;

	public List<Nivel> findAll() {
		return repository.findAll();
	}
}
