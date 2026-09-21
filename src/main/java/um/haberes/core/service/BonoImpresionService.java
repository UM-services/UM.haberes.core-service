/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.model.BonoImpresionEntity;
import um.haberes.core.repository.JpaBonoImpresionRepository;
import um.haberes.core.util.Tool;

/**
 * @author daniel
 *
 */
@Service
public class BonoImpresionService {
	
	@Autowired
	private JpaBonoImpresionRepository repository;

	public BonoImpresionEntity add(BonoImpresionEntity bonoimpresion) {
		bonoimpresion.setFecha(Tool.hourAbsoluteArgentina());
		repository.save(bonoimpresion);
		return bonoimpresion;
	}

	public List<BonoImpresionEntity> findAllByLegajoIdAndAnhoAndMesOrderByFechaDesc(Long legajoId, Integer anho,
			Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMesOrderByFechaDesc(legajoId, anho, mes);
	}
}
