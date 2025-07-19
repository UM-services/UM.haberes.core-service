/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.CursoFusion;
import um.haberes.core.repository.CursoFusionRepository;

/**
 * @author daniel
 *
 */
@Service
public class CursoFusionService {

	@Autowired
	private CursoFusionRepository repository;

	public List<CursoFusion> findAllByLegajoId(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	public List<CursoFusion> findAllByLegajoIdAndFacultadId(Long legajoId, Integer anho, Integer mes,
			Integer facultadId) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndFacultadId(legajoId, anho, mes, facultadId);
	}

	public List<CursoFusion> findAllByAnhoAndMes(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

	public CursoFusion add(CursoFusion cursofusion) {
		repository.save(cursofusion);
		return cursofusion;
	}

	@Transactional
	public void deleteAllByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(Long legajoId, Integer anho, Integer mes,
			Integer facultadId, Integer geograficaId) {
		repository.deleteAllByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(legajoId, anho, mes, facultadId,
				geograficaId);
	}

	@Transactional
	public void deleteByCursoFusionId(Long cursoFusionId) {
		repository.deleteByCursoFusionId(cursoFusionId);
	}

	@Transactional
	public List<CursoFusion> saveAll(List<CursoFusion> fusiones) {
		fusiones = repository.saveAll(fusiones);
		return fusiones;
	}

	@Transactional
	public void deleteAllByLegajoIdInAndAnhoAndMes(List<Long> legajos, Integer anho, Integer mes) {
		repository.deleteAllByLegajoIdInAndAnhoAndMes(legajos, anho, mes);
	}

	@Transactional
	public void deleteAllByLegajoIdAndPeriodo(Long legajoId, Integer anho, Integer mes) {
		repository.deleteAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);

	}

}
