/**
 * 
 */
package um.haberes.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.BonoImpresion;
import um.haberes.core.repository.BonoImpresionRepository;
import um.haberes.core.util.Tool;

/**
 * @author daniel
 *
 */
@Service
public class BonoImpresionService {
	
	@Autowired
	private BonoImpresionRepository repository;

	public BonoImpresion add(BonoImpresion bonoimpresion) {
		bonoimpresion.setFecha(Tool.hourAbsoluteArgentina());
		repository.save(bonoimpresion);
		return bonoimpresion;
	}
}
