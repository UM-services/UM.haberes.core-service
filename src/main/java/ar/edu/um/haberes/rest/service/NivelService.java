/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.Nivel;
import ar.edu.um.haberes.rest.repository.INivelRepository;

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
