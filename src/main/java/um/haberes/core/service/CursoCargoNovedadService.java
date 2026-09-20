/**
 * 
 */
package um.haberes.core.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.CursoCargoNovedadException;
import um.haberes.core.model.CursoCargoNovedadEntity;
import um.haberes.core.repository.JpaCursoCargoNovedadRepository;
import um.haberes.core.util.Jsonifier;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class CursoCargoNovedadService {

	@Autowired
	private JpaCursoCargoNovedadRepository repository;

	public List<CursoCargoNovedadEntity> findAllPendientes(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndAutorizadoAndRechazado(anho, mes, (byte) 0, (byte) 0);
	}

	public List<CursoCargoNovedadEntity> findAllPendientesAlta(Integer anho, Integer mes) {
		var pendientesAlta = repository
				.findAllByAnhoAndMesAndAutorizadoAndRechazado(anho, mes, (byte) 0, (byte) 0,
						Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending())
								.and(Sort.by("cargoTipo.aCargo").descending()).and(Sort.by("cargoTipoId").ascending()))
				.stream().filter(cargo -> cargo.getAlta() == 1 || cargo.getCambio() == 1).collect(Collectors.toList());
		log.debug("pendientesAlta -> {}", Jsonifier.builder(pendientesAlta).build());
        return pendientesAlta;
	}

	public List<CursoCargoNovedadEntity> findAllCursoPendientesAlta(Long cursoId, Integer anho, Integer mes) {
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

	public List<CursoCargoNovedadEntity> findAllAutorizadosAlta(Integer anho, Integer mes) {
		return repository
				.findAllByAnhoAndMesAndAutorizado(anho, mes, (byte) 1,
						Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending())
								.and(Sort.by("cargoTipo.aCargo").descending()).and(Sort.by("cargoTipoId").ascending()))
				.stream().filter(cargo -> cargo.getAlta() == 1 || cargo.getCambio() == 1).collect(Collectors.toList());
	}

	public List<CursoCargoNovedadEntity> findAllRechazadosAlta(Integer anho, Integer mes) {
		return repository
				.findAllByAnhoAndMesAndRechazado(anho, mes, (byte) 1,
						Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending())
								.and(Sort.by("cargoTipo.aCargo").descending()).and(Sort.by("cargoTipoId").ascending()))
				.stream().filter(cargo -> cargo.getAlta() == 1 || cargo.getCambio() == 1).collect(Collectors.toList());
	}

	public List<CursoCargoNovedadEntity> findAllPendientesBaja(Integer anho, Integer mes) {
		var pendientesBaja = repository.findAllByAnhoAndMesAndBajaAndAutorizadoAndRechazado(anho, mes, (byte) 1, (byte) 0, (byte) 0,
				Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending())
						.and(Sort.by("cargoTipo.aCargo").descending()).and(Sort.by("cargoTipoId").ascending()));
		log.debug("pendientesBaja -> {}",  Jsonifier.builder(pendientesBaja).build());
        return pendientesBaja;
	}

	public List<CursoCargoNovedadEntity> findAllCursoPendientesBaja(Long cursoId, Integer anho, Integer mes) {
		return repository.findAllByCursoIdAndAnhoAndMesAndBajaAndAutorizadoAndRechazado(cursoId, anho, mes, (byte) 1,
				(byte) 0, (byte) 0, Sort.by("cargoTipo.aCargo").descending().and(Sort.by("cargoTipoId").ascending()));
	}

	public List<CursoCargoNovedadEntity> findAllAutorizadosBaja(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndBajaAndAutorizado(anho, mes, (byte) 1, (byte) 1,
				Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending())
						.and(Sort.by("cargoTipo.aCargo").descending()).and(Sort.by("cargoTipoId").ascending()));
	}

	public List<CursoCargoNovedadEntity> findAllRechazadosBaja(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndBajaAndRechazado(anho, mes, (byte) 1, (byte) 1,
				Sort.by("persona.apellido").ascending().and(Sort.by("persona.nombre").ascending())
						.and(Sort.by("cargoTipo.aCargo").descending()).and(Sort.by("cargoTipoId").ascending()));
	}

	public List<CursoCargoNovedadEntity> findAllAutorizadosLegajo(Long legajoId, Long cursoId, Integer anho, Integer mes) {
		return repository.findAllByCursoIdAndAnhoAndMesAndAutorizadoAndLegajoId(cursoId, anho, mes, (byte) 1, legajoId);
	}

	public List<CursoCargoNovedadEntity> findAllRechazadosLegajo(Long legajoId, Long cursoId, Integer anho, Integer mes) {
		return repository.findAllByCursoIdAndAnhoAndMesAndRechazadoAndLegajoId(cursoId, anho, mes, (byte) 1, legajoId);
	}

	public List<CursoCargoNovedadEntity> findAllPendientesLegajo(Long legajoId, Long cursoId, Integer anho, Integer mes) {
		var pendientesLegajo = repository.findAllByCursoIdAndAnhoAndMesAndAutorizadoAndRechazadoAndLegajoId(cursoId, anho, mes,
				(byte) 0, (byte) 0, legajoId);
		log.debug("pendientesLegajo -> {}", Jsonifier.builder(pendientesLegajo).build());
        return pendientesLegajo;
	}

	public List<CursoCargoNovedadEntity> findAllByFacultad(Integer facultadId, Integer anho, Integer mes) {
		return repository.findAllByCursoFacultadIdAndAnhoAndMes(facultadId, anho, mes);
	}

	public List<CursoCargoNovedadEntity> findAllByFacultadAndGeograficaAndAlta(Integer facultadId, Integer geograficaId, Integer anho, Integer mes) {
		return repository.findAllByCursoFacultadIdAndCursoGeograficaIdAndAnhoAndMesAndAltaOrderByCursoNombre(facultadId, geograficaId, anho, mes, (byte) 1);
	}

	public List<CursoCargoNovedadEntity> findAllByFacultadAndGeograficaAndCambio(Integer facultadId, Integer geograficaId, Integer anho, Integer mes) {
		return repository.findAllByCursoFacultadIdAndCursoGeograficaIdAndAnhoAndMesAndCambioOrderByCursoNombre(facultadId, geograficaId, anho, mes, (byte) 1);
	}

	public List<CursoCargoNovedadEntity> findAllByFacultadAndGeograficaAndBaja(Integer facultadId, Integer geograficaId, Integer anho, Integer mes) {
		return repository.findAllByCursoFacultadIdAndCursoGeograficaIdAndAnhoAndMesAndBajaOrderByCursoNombre(facultadId, geograficaId, anho, mes, (byte) 1);
	}

	public CursoCargoNovedadEntity findByCursoCargoNovedadId(Long cursoCargoNovedadId) {
		var cursoCargoNovedad = repository.findByCursoCargoNovedadId(cursoCargoNovedadId)
				.orElseThrow(() -> new CursoCargoNovedadException(cursoCargoNovedadId));
		log.debug("CursoCargoNovedadEntity -> {}",  cursoCargoNovedad.jsonify());
        return cursoCargoNovedad;
	}

	public CursoCargoNovedadEntity findByLegajo(Long legajoId, Long cursoId, Integer anho, Integer mes) {
		var cursoCargoNovedad = repository.findByLegajoIdAndCursoIdAndAnhoAndMes(legajoId, cursoId, anho, mes)
				.orElseThrow(() -> new CursoCargoNovedadException(legajoId, cursoId, anho, mes));
		log.debug("CursoCargoNovedadEntity -> {}",  cursoCargoNovedad.jsonify());
        return cursoCargoNovedad;
	}

	public CursoCargoNovedadEntity findByUnique(Long cursoId, Integer anho, Integer mes, Integer cargoTipoId, Long legajoId) {
		var cursoCargoNovedad = repository.findByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(cursoId, anho, mes, cargoTipoId, legajoId)
				.orElseThrow(() -> new CursoCargoNovedadException(cursoId, anho, mes, cargoTipoId, legajoId));
		log.debug("CursoCargoNovedadEntity -> {}",  cursoCargoNovedad.jsonify());
        return cursoCargoNovedad;
	}

	public CursoCargoNovedadEntity add(CursoCargoNovedadEntity cursoCargoNovedad) {
		if (cursoCargoNovedad.getRespuesta() == null) {
			cursoCargoNovedad.setRespuesta("");
		}
		log.debug("CursoCargoNovedadEntity (before) -> {}",  cursoCargoNovedad.jsonify());
        cursoCargoNovedad = repository.save(cursoCargoNovedad);
		log.debug("CursoCargoNovedadEntity (after) -> {}",  cursoCargoNovedad.jsonify());
		return cursoCargoNovedad;
	}

	public CursoCargoNovedadEntity update(CursoCargoNovedadEntity newCursoCargoNovedad, Long cursoCargoNovedadId) {
		return repository.findByCursoCargoNovedadId(cursoCargoNovedadId).map(cursoCargoNovedad -> {
			cursoCargoNovedad = new CursoCargoNovedadEntity(cursoCargoNovedadId, newCursoCargoNovedad.getCursoId(),
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
