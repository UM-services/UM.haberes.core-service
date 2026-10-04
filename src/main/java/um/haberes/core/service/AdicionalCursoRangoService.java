/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.AdicionalCursoRangoException;
import um.haberes.core.model.AdicionalCursoRangoEntity;
import um.haberes.core.repository.JpaAdicionalCursoRangoRepository;

/**
 * @author daniel
 *
 */
@Service
public class AdicionalCursoRangoService {

	@Autowired
	private JpaAdicionalCursoRangoRepository repository;

	public List<AdicionalCursoRangoEntity> findAllByAdicionalCursoTabla(Long adicionalCursoTablaId) {
		return repository.findAllByAdicionalCursoTablaIdOrderByHorasDesde(adicionalCursoTablaId);
	}

	public AdicionalCursoRangoEntity findByAdicionalCursoRangoId(Long adicionalCursoRangoId) {
		return repository.findByAdicionalCursoRangoId(adicionalCursoRangoId)
				.orElseThrow(() -> new AdicionalCursoRangoException(adicionalCursoRangoId));
	}

	public AdicionalCursoRangoEntity add(AdicionalCursoRangoEntity adicionalCursoRango) {
		return repository.save(adicionalCursoRango);
	}

	@Transactional
	public void deleteByAdicionalCursoRangoId(Long adicionalCursoRangoId) {
		repository.deleteByAdicionalCursoRangoId(adicionalCursoRangoId);
	}

}
