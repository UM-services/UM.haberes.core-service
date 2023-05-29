/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.AcreditacionNotFoundException;
import um.haberes.rest.exception.CursoDesarraigoNotFoundException;
import um.haberes.rest.model.CursoDesarraigo;
import um.haberes.rest.repository.ICursoDesarraigoRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class CursoDesarraigoService {
	@Autowired
	private ICursoDesarraigoRepository repository;

	public List<CursoDesarraigo> findAll() {
		return repository.findAll();
	}

	public List<CursoDesarraigo> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	public List<CursoDesarraigo> findAllByVersion(Long legajoId, Integer anho, Integer mes, Integer version) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndVersion(legajoId, anho, mes, version);
	}

	public CursoDesarraigo findByCursoDesarraigoId(Long cursoDesarraigoId) {
		return repository.findByCursoDesarraigoId(cursoDesarraigoId)
				.orElseThrow(() -> new CursoDesarraigoNotFoundException(cursoDesarraigoId));
	}

	public CursoDesarraigo findByUnique(Long legajoId, Integer anho, Integer mes, Long cursoId) {
		return repository.findByLegajoIdAndAnhoAndMesAndCursoId(legajoId, anho, mes, cursoId)
				.orElseThrow(() -> new CursoDesarraigoNotFoundException(legajoId, anho, mes, cursoId));
	}

	@Transactional
	public void delete(Long cursoDesarraigoId) {
		repository.deleteById(cursoDesarraigoId);
	}

	public CursoDesarraigo add(CursoDesarraigo cursoDesarraigo) {
		repository.save(cursoDesarraigo);
		log.debug("CursoDesarraigo -> " + cursoDesarraigo);
		return cursoDesarraigo;
	}

	public CursoDesarraigo update(CursoDesarraigo newCursoDesarraigo, Long cursoDesarraigoId) {
		return repository.findByCursoDesarraigoId(cursoDesarraigoId).map(cursoDesarraigo -> {
			cursoDesarraigo = new CursoDesarraigo(cursoDesarraigoId, newCursoDesarraigo.getLegajoId(),
					newCursoDesarraigo.getAnho(), newCursoDesarraigo.getMes(), newCursoDesarraigo.getCursoId(),
					newCursoDesarraigo.getGeograficaId(), newCursoDesarraigo.getImporte(),
					newCursoDesarraigo.getVersion(), newCursoDesarraigo.getCurso(), newCursoDesarraigo.getPersona(),
					newCursoDesarraigo.getGeografica());
			cursoDesarraigo = repository.save(cursoDesarraigo);
			log.debug("CursoDesarraigo -> " + cursoDesarraigo);
			return cursoDesarraigo;
		}).orElseThrow(() -> new AcreditacionNotFoundException(cursoDesarraigoId));
	}

	public List<CursoDesarraigo> saveAll(List<CursoDesarraigo> cursos) {
		cursos = repository.saveAll(cursos);
		return cursos;
	}

}
