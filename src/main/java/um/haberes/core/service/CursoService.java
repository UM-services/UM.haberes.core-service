/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.CursoException;
import um.haberes.core.kotlin.model.Curso;
import um.haberes.core.kotlin.model.CursoCargo;
import um.haberes.core.repository.ICursoRepository;

/**
 * @author daniel
 *
 */
@Service
public class CursoService {

	private final ICursoRepository repository;

	@Autowired
	public CursoService(ICursoRepository repository) {
		this.repository = repository;
	}

	public List<Curso> findAll() {
		return repository.findAll();
	}

	public List<Curso> findAllByGeograficaAndConditions(Integer facultadId, Integer geograficaId,
			List<String> conditions) {
		return repository.findAllByFacultadIdAndGeograficaIdAndConditions(facultadId, geograficaId, conditions);
	}

	public List<Curso> findAllByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId) {
		return repository.findAllByFacultadIdAndGeograficaId(facultadId, geograficaId, Sort.by("nombre").ascending());
	}

	public List<Curso> findAllByCursoIdIn(List<Long> ids) {
		return repository.findAllByCursoIdIn(ids);
	}

	public List<Curso> findAllByFacultadId(Integer facultadId) {
		return repository.findAllByFacultadId(facultadId);
	}

	public List<Curso> findAllByFacultadIdAndGeograficaIdAndAnhoAndMes(Integer facultadId, Integer geograficaId, Integer anho, Integer mes, CursoCargoService cursoCargoService) {
		List<Long> cursoIds = cursoCargoService.findAllByAnhoAndMes(anho, mes).stream().map(CursoCargo::getCursoId).toList();
		return repository.findAllByFacultadIdAndGeograficaIdAndCursoIdInOrderByNombre(facultadId, geograficaId, cursoIds);
	}

	public Curso findByCursoId(Long cursoId) {
		return repository.findByCursoId(cursoId).orElseThrow(() -> new CursoException(cursoId));
	}

	public Curso add(Curso curso) {
		return repository.save(curso);
	}

	public Curso update(Curso newCurso, Long cursoId) {
		return repository.findByCursoId(cursoId).map(curso -> {
			curso = new Curso(cursoId, newCurso.getNombre(), newCurso.getFacultadId(), newCurso.getGeograficaId(),
					newCurso.getAnual(), newCurso.getSemestre1(), newCurso.getSemestre2(), newCurso.getNivelId(),
					newCurso.getAdicionalCargaHoraria(), newCurso.getFacultad(), newCurso.getGeografica(),
					newCurso.getNivel());
			repository.save(curso);
			return curso;
		}).orElseThrow(() -> new CursoException(cursoId));
	}

	@Transactional
	public void deleteByCursoId(Long cursoId) {
		repository.deleteByCursoId(cursoId);
	}

}
