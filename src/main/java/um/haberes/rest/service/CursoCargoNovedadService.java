/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.CursoCargoNovedadException;
import um.haberes.rest.kotlin.model.CursoCargoNovedad;
import um.haberes.rest.repository.ICursoCargoNovedadRepository;

/**
 * @author daniel
 *
 */
@Service
public class CursoCargoNovedadService {

	@Autowired
	private ICursoCargoNovedadRepository repository;

	public List<CursoCargoNovedad> findAllPendientes(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndAutorizadoAndRechazado(anho, mes, (byte) 0, (byte) 0);
	}

	public List<CursoCargoNovedad> findAllPendientesAlta(Integer anho, Integer mes) {
		return repository
				.findAllByAnhoAndMesAndAutorizadoAndRechazado(anho, mes, (byte) 0, (byte) 0,
						Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending())
								.and(Sort.by("cargoTipo.aCargo").descending()).and(Sort.by("cargoTipoId").ascending()))
				.stream().filter(cargo -> cargo.getAlta() == 1 || cargo.getCambio() == 1).collect(Collectors.toList());
	}

	public List<CursoCargoNovedad> findAllCursoPendientesAlta(Long cursoId, Integer anho, Integer mes) {
		return Stream.concat(repository
				.findAllByCursoIdAndAnhoAndMesAndAltaAndAutorizadoAndRechazado(cursoId, anho, mes, (byte) 1, (byte) 0,
						(byte) 0, Sort.by("cargoTipo.aCargo").descending().and(Sort.by("cargoTipoId").ascending()))
				.stream(),
				repository
						.findAllByCursoIdAndAnhoAndMesAndCambioAndAutorizadoAndRechazado(cursoId, anho, mes, (byte) 1,
								(byte) 0, (byte) 0,
								Sort.by("cargoTipo.aCargo").descending().and(Sort.by("cargoTipoId").ascending()))
						.stream())
				.collect(Collectors.toList());
	}

	public List<CursoCargoNovedad> findAllAutorizadosAlta(Integer anho, Integer mes) {
		return repository
				.findAllByAnhoAndMesAndAutorizado(anho, mes, (byte) 1,
						Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending())
								.and(Sort.by("cargoTipo.aCargo").descending()).and(Sort.by("cargoTipoId").ascending()))
				.stream().filter(cargo -> cargo.getAlta() == 1 || cargo.getCambio() == 1).collect(Collectors.toList());
	}

	public List<CursoCargoNovedad> findAllRechazadosAlta(Integer anho, Integer mes) {
		return repository
				.findAllByAnhoAndMesAndRechazado(anho, mes, (byte) 1,
						Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending())
								.and(Sort.by("cargoTipo.aCargo").descending()).and(Sort.by("cargoTipoId").ascending()))
				.stream().filter(cargo -> cargo.getAlta() == 1 || cargo.getCambio() == 1).collect(Collectors.toList());
	}

	public List<CursoCargoNovedad> findAllPendientesBaja(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndBajaAndAutorizadoAndRechazado(anho, mes, (byte) 1, (byte) 0, (byte) 0,
				Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending())
						.and(Sort.by("cargoTipo.aCargo").descending()).and(Sort.by("cargoTipoId").ascending()));
	}

	public List<CursoCargoNovedad> findAllCursoPendientesBaja(Long cursoId, Integer anho, Integer mes) {
		return repository.findAllByCursoIdAndAnhoAndMesAndBajaAndAutorizadoAndRechazado(cursoId, anho, mes, (byte) 1,
				(byte) 0, (byte) 0, Sort.by("cargoTipo.aCargo").descending().and(Sort.by("cargoTipoId").ascending()));
	}

	public List<CursoCargoNovedad> findAllAutorizadosBaja(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndBajaAndAutorizado(anho, mes, (byte) 1, (byte) 1,
				Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending())
						.and(Sort.by("cargoTipo.aCargo").descending()).and(Sort.by("cargoTipoId").ascending()));
	}

	public List<CursoCargoNovedad> findAllRechazadosBaja(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndBajaAndRechazado(anho, mes, (byte) 1, (byte) 1,
				Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending())
						.and(Sort.by("cargoTipo.aCargo").descending()).and(Sort.by("cargoTipoId").ascending()));
	}

	public List<CursoCargoNovedad> findAllAutorizadosLegajo(Long legajoId, Long cursoId, Integer anho, Integer mes) {
		return repository.findAllByCursoIdAndAnhoAndMesAndAutorizadoAndLegajoId(cursoId, anho, mes, (byte) 1, legajoId);
	}

	public List<CursoCargoNovedad> findAllRechazadosLegajo(Long legajoId, Long cursoId, Integer anho, Integer mes) {
		return repository.findAllByCursoIdAndAnhoAndMesAndRechazadoAndLegajoId(cursoId, anho, mes, (byte) 1, legajoId);
	}

	public List<CursoCargoNovedad> findAllPendientesLegajo(Long legajoId, Long cursoId, Integer anho, Integer mes) {
		return repository.findAllByCursoIdAndAnhoAndMesAndAutorizadoAndRechazadoAndLegajoId(cursoId, anho, mes,
				(byte) 0, (byte) 0, legajoId);
	}

	public CursoCargoNovedad findByCursoCargoNovedadId(Long cursoCargoNovedadId) {
		return repository.findByCursoCargoNovedadId(cursoCargoNovedadId)
				.orElseThrow(() -> new CursoCargoNovedadException(cursoCargoNovedadId));
	}

	public CursoCargoNovedad findByLegajo(Long legajoId, Long cursoId, Integer anho, Integer mes) {
		return repository.findByLegajoIdAndCursoIdAndAnhoAndMes(legajoId, cursoId, anho, mes)
				.orElseThrow(() -> new CursoCargoNovedadException(legajoId, cursoId, anho, mes));
	}

	public CursoCargoNovedad findByUnique(Long cursoId, Integer anho, Integer mes, Integer cargoTipoId, Long legajoId) {
		return repository.findByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(cursoId, anho, mes, cargoTipoId, legajoId)
				.orElseThrow(() -> new CursoCargoNovedadException(cursoId, anho, mes, cargoTipoId, legajoId));
	}

	public CursoCargoNovedad add(CursoCargoNovedad cursoCargoNovedad) {
		repository.save(cursoCargoNovedad);
		return cursoCargoNovedad;
	}

	public CursoCargoNovedad update(CursoCargoNovedad newCursoCargoNovedad, Long cursoCargoNovedadId) {
		return repository.findByCursoCargoNovedadId(cursoCargoNovedadId).map(cursoCargoNovedad -> {
			cursoCargoNovedad = new CursoCargoNovedad(cursoCargoNovedadId, newCursoCargoNovedad.getCursoId(),
					newCursoCargoNovedad.getAnho(), newCursoCargoNovedad.getMes(),
					newCursoCargoNovedad.getCargoTipoId(), newCursoCargoNovedad.getLegajoId(),
					newCursoCargoNovedad.getHorasSemanales(), newCursoCargoNovedad.getHorasTotales(),
					newCursoCargoNovedad.getDesarraigo(), newCursoCargoNovedad.getAlta(),
					newCursoCargoNovedad.getBaja(), newCursoCargoNovedad.getCambio(),
					newCursoCargoNovedad.getSolicitud(), newCursoCargoNovedad.getAutorizado(),
					newCursoCargoNovedad.getRechazado(), newCursoCargoNovedad.getRespuesta(),
					newCursoCargoNovedad.getTransferido(), newCursoCargoNovedad.getCurso(),
					newCursoCargoNovedad.getCargoTipo(), newCursoCargoNovedad.getPersona());
			repository.save(cursoCargoNovedad);
			return cursoCargoNovedad;
		}).orElseThrow(() -> new CursoCargoNovedadException(cursoCargoNovedadId));
	}

	@Transactional
	public void deleteAllByLegajoPendiente(Long legajoId, Long cursoId, Integer anho, Integer mes) {
		repository.deleteAllByLegajoIdAndCursoIdAndAnhoAndMesAndAutorizadoAndRechazado(legajoId, cursoId, anho, mes,
				(byte) 0, (byte) 0);
	}

	@Transactional
    public void delete(Long cursoCargoNovedadId) {
		repository.deleteByCursoCargoNovedadId(cursoCargoNovedadId);
    }
}
