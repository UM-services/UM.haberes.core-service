/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.AdicionalCursoRangoNotFoundException;
import ar.edu.um.haberes.rest.model.AdicionalCursoRango;
import ar.edu.um.haberes.rest.repository.IAdicionalCursoRangoRepository;

/**
 * @author daniel
 *
 */
@Service
public class AdicionalCursoRangoService {

	@Autowired
	private IAdicionalCursoRangoRepository repository;

	public List<AdicionalCursoRango> findAllByAdicionalCursoTabla(Long adicionalCursoTablaId) {
		return repository.findAllByAdicionalCursoTablaIdOrderByHorasDesde(adicionalCursoTablaId);
	}

	public AdicionalCursoRango findByAdicionalCursoRangoId(Long adicionalCursoRangoId) {
		return repository.findByAdicionalCursoRangoId(adicionalCursoRangoId)
				.orElseThrow(() -> new AdicionalCursoRangoNotFoundException(adicionalCursoRangoId));
	}

	public AdicionalCursoRango add(AdicionalCursoRango adicionalCursoRango) {
		return repository.save(adicionalCursoRango);
	}

	@Transactional
	public void deleteByAdicionalCursoRangoId(Long adicionalCursoRangoId) {
		repository.deleteByAdicionalCursoRangoId(adicionalCursoRangoId);
	}

}
