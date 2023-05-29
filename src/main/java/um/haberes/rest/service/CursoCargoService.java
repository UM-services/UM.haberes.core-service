/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.CursoCargoNotFoundException;
import um.haberes.rest.model.CursoCargo;
import um.haberes.rest.repository.ICursoCargoRepository;

/**
 * @author daniel
 *
 */
@Service
public class CursoCargoService {

	@Autowired
	private ICursoCargoRepository repository;

	public List<CursoCargo> findAllByLegajoAndNivel(Long legajoId, Integer anho, Integer mes, Integer nivelId) {
		List<CursoCargo> cursoCargos = this.findAllByLegajo(legajoId, anho, mes);
		return cursoCargos.stream().filter(c -> c.getCurso().getNivelId() == nivelId).collect(Collectors.toList());
	}

	public List<CursoCargo> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes,
				Sort.by("curso.facultadId").ascending().and(Sort.by("curso.geograficaId").ascending())
						.and(Sort.by("cargoTipoId").ascending()).and(Sort.by("curso.nombre").ascending()));
	}

	public List<CursoCargo> findAllByLegajoDesarraigo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndDesarraigo(legajoId, anho, mes, (byte) 1);
	}

	public List<CursoCargo> findAnyByCursoId(Long cursoId) {
		return repository.findTopByCursoId(cursoId);
	}

	public List<CursoCargo> findAllByCurso(Long cursoId, Integer anho, Integer mes) {
		return repository.findAllByCursoIdAndAnhoAndMes(cursoId, anho, mes,
				Sort.by("cargoTipo.aCargo").descending().and(Sort.by("cargoTipoId").ascending()));
	}

	public List<CursoCargo> findAllByFacultad(Long legajoId, Integer anho, Integer mes, Integer facultadId) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes, null).stream()
				.filter(c -> c.getCurso().getFacultadId() == facultadId).collect(Collectors.toList());
	}

	public List<CursoCargo> findAllByCargoTipo(Long legajoId, Integer anho, Integer mes, Integer facultadId,
			Integer geograficaId, Byte anual, Byte semestre1, Byte semestre2, Integer cargoTipoId) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndCargoTipoId(legajoId, anho, mes, cargoTipoId).stream()
				.filter(c -> c.getCurso().getFacultadId() == facultadId
						&& c.getCurso().getGeograficaId() == geograficaId && c.getCurso().getAnual() == anual
						&& c.getCurso().getSemestre1() == semestre1 && c.getCurso().getSemestre2() == semestre2)
				.collect(Collectors.toList());
	}

	public List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaIdAndCargoTipoId(Long legajoId,
			Integer anho, Integer mes, Integer facultadId, Integer geograficaId, Integer cargoTipoId) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndCargoTipoId(legajoId, anho, mes, cargoTipoId).stream()
				.filter(c -> c.getCurso().getFacultadId() == facultadId
						&& c.getCurso().getGeograficaId() == geograficaId)
				.collect(Collectors.toList());
	}

	public List<CursoCargo> findAnyByAnhoAndMes(Integer anho, Integer mes) {
		return repository.findTopByAnhoAndMes(anho, mes);
	}

	public List<CursoCargo> findAllByAnhoAndMes(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

	public List<CursoCargo> findAllByAnhoAndMesAndDesarraigo(Integer anho, Integer mes, Byte desarraigo) {
		return repository.findAllByAnhoAndMesAndDesarraigo(anho, mes, desarraigo);
	}

	public List<CursoCargo> findAllByLegajoWithAdicional(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes, Sort.by("cursoCargoId")).stream()
				.filter(cursoCargo -> cursoCargo.getCurso().getAdicionalCargaHoraria() == 1)
				.collect(Collectors.toList());
	}

	public CursoCargo findByCursoCargoId(Long cursoCargoId) {
		return repository.findByCursoCargoId(cursoCargoId)
				.orElseThrow(() -> new CursoCargoNotFoundException(cursoCargoId));
	}

	public CursoCargo findByUnique(Long cursoId, Integer anho, Integer mes, Integer cargoTipoId, Long legajoId) {
		return repository.findByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(cursoId, anho, mes, cargoTipoId, legajoId)
				.orElseThrow(() -> new CursoCargoNotFoundException(cursoId, anho, mes, cargoTipoId, legajoId));
	}

	public CursoCargo findByLegajo(Long cursoId, Integer anho, Integer mes, Long legajoId) {
		return repository.findByCursoIdAndAnhoAndMesAndLegajoId(cursoId, anho, mes, legajoId)
				.orElseThrow(() -> new CursoCargoNotFoundException(cursoId, anho, mes, legajoId));
	}

	public CursoCargo add(CursoCargo cursoCargo) {
		repository.save(cursoCargo);
		return cursoCargo;
	}

	public CursoCargo update(CursoCargo newCursoCargo, Long cursoCargoId) {
		return repository.findByCursoCargoId(cursoCargoId).map(cursoCargo -> {
			cursoCargo = new CursoCargo(cursoCargoId, newCursoCargo.getCursoId(), newCursoCargo.getAnho(),
					newCursoCargo.getMes(), newCursoCargo.getCargoTipoId(), newCursoCargo.getLegajoId(),
					newCursoCargo.getHorasSemanales(), newCursoCargo.getHorasTotales(),
					newCursoCargo.getDesignacionTipoId(), newCursoCargo.getCategoriaId(), newCursoCargo.getDesarraigo(),
					newCursoCargo.getCursoCargoNovedadId(), newCursoCargo.getCurso(), newCursoCargo.getCargoTipo(),
					newCursoCargo.getPersona(), newCursoCargo.getDesignacionTipo(), newCursoCargo.getCategoria());
			repository.save(cursoCargo);
			return cursoCargo;
		}).orElseThrow(() -> new CursoCargoNotFoundException(cursoCargoId));
	}

	@Transactional
	public void deleteByCursoCargoId(Long cursoCargoId) {
		repository.deleteByCursoCargoId(cursoCargoId);
	}

	@Transactional
	public void deleteByUnique(Long cursoId, Integer anho, Integer mes, Integer cargoTipoId, Long legajoId) {
		repository.deleteByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(cursoId, anho, mes, cargoTipoId, legajoId);
	}

	@Transactional
	public List<CursoCargo> saveall(List<CursoCargo> cursoCargos) {
		cursoCargos = repository.saveAll(cursoCargos);
		return cursoCargos;
	}

}
