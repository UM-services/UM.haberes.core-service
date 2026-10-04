/**
 * 
 */
package um.haberes.core.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import um.haberes.core.exception.AnotadorException;
import um.haberes.core.hexagonal.personas.persona.application.service.PersonaService;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.model.AnotadorEntity;
import um.haberes.core.repository.JpaAnotadorRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class AnotadorService {

	private final JpaAnotadorRepository repository;
	private final PersonaService personaservice;

	public AnotadorService(JpaAnotadorRepository repository,
						   PersonaService personaservice) {
		this.repository = repository;
		this.personaservice = personaservice;
	}

	public List<AnotadorEntity> findAllByLegajo(Long legajoId) {
		return repository.findAllByLegajoIdOrderByAnotadorIdDesc(legajoId);
	}

	public List<AnotadorEntity> findPendientes(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndAutorizadoAndRechazadoOrderByPersonaApellidoAscPersonaNombreAsc(
			anho, mes, (byte) 0, (byte) 0);
	}

	public List<AnotadorEntity> findPendientesFiltro(Integer anho, Integer mes, String filtro) {
		List<Long> legajos = personaservice.findAllByFiltro(filtro).stream()
				.map(Persona::getLegajoId)
				.collect(Collectors.toList());
		log.debug("Legajos -> {}", legajos);
		return repository.findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndLegajoIdInOrderByPersonaApellidoAscPersonaNombreAsc(
				anho, mes, (byte) 0, (byte) 0, legajos);
	}

	public List<AnotadorEntity> findPendientesByFacultad(Integer facultadId, Integer anho, Integer mes) {
		return repository.findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndFacultadIdOrderByPersonaApellidoAscPersonaNombreAsc(
			anho, mes, (byte) 0, (byte) 0, facultadId);
	}

	public List<AnotadorEntity> findAutorizadosByFacultad(Integer facultadId, Integer anho, Integer mes) {
		return repository.findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndFacultadIdOrderByPersonaApellidoAscPersonaNombreAsc(
			anho, mes, (byte) 1, (byte) 0, facultadId);
	}

	public List<AnotadorEntity> findRechazadosByFacultad(Integer facultadId, Integer anho, Integer mes) {
		return repository.findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndFacultadIdOrderByPersonaApellidoAscPersonaNombreAsc(
			anho, mes, (byte) 0, (byte) 1, facultadId);
	}

	public List<AnotadorEntity> findRevisados(Integer anho, Integer mes) {
		return repository.findTop1000ByAnhoAndMesOrderByPersonaApellidoAscPersonaNombreAsc(anho, mes).stream()
				.filter(anotador -> anotador.getAutorizado() == 1 || anotador.getRechazado() == 1)
				.collect(Collectors.toList());
	}

	public List<AnotadorEntity> findRevisadosFiltro(Integer anho, Integer mes, String filtro) {
		List<Long> legajos = personaservice.findAllByFiltro(filtro).stream()
				.map(Persona::getLegajoId)
				.collect(Collectors.toList());
		return repository.findTop1000ByAnhoAndMesAndLegajoIdInOrderByPersonaApellidoAscPersonaNombreAsc(anho, mes, legajos).stream()
				.filter(anotador -> anotador.getAutorizado() == 1 || anotador.getRechazado() == 1)
				.collect(Collectors.toList());
	}

	public List<AnotadorEntity> findRevisadosByFacultad(Integer facultadId, Integer anho, Integer mes) {
		return repository.findTop1000ByAnhoAndMesAndFacultadIdOrderByPersonaApellidoAscPersonaNombreAsc(anho, mes, facultadId).stream()
				.filter(anotador -> anotador.getAutorizado() == 1 || anotador.getRechazado() == 1)
				.collect(Collectors.toList());
	}

	public AnotadorEntity findByAnotadorId(Long anotadorId) {
		return repository.findByAnotadorId(anotadorId).orElseThrow(() -> new AnotadorException(anotadorId));
	}

	public AnotadorEntity add(AnotadorEntity anotador) {
		if (anotador.getIpVisado() == null) {
			anotador.setIpVisado("");
		}
		if (anotador.getRespuesta() == null) {
			anotador.setRespuesta("");
		}
		repository.save(anotador);
		return anotador;
	}

	public AnotadorEntity update(AnotadorEntity newAnotador, Long anotadorId) {
		return repository.findByAnotadorId(anotadorId).map(anotador -> {
			anotador = new AnotadorEntity(anotadorId, newAnotador.getLegajoId(), newAnotador.getAnho(), newAnotador.getMes(),
					newAnotador.getFacultadId(), newAnotador.getAnotacion(), newAnotador.getVisado(),
					newAnotador.getIpVisado(), newAnotador.getUser(), newAnotador.getRespuesta(),
					newAnotador.getAutorizado(), newAnotador.getRechazado(), newAnotador.getRectorado(),
					newAnotador.getTransferido(), newAnotador.getPersona(), newAnotador.getFacultad());
			repository.save(anotador);
			return anotador;
		}).orElseThrow(() -> new AnotadorException(anotadorId));
	}

}
