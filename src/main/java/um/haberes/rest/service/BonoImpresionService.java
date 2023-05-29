/**
 * 
 */
package um.haberes.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.model.BonoImpresion;
import um.haberes.rest.repository.IBonoImpresionRepository;
import um.haberes.rest.util.Tool;

/**
 * @author daniel
 *
 */
@Service
public class BonoImpresionService {
	
	@Autowired
	private IBonoImpresionRepository repository;

	public BonoImpresion add(BonoImpresion bonoimpresion) {
		bonoimpresion.setFecha(Tool.hourAbsoluteArgentina());
		repository.save(bonoimpresion);
		return bonoimpresion;
	}
}
