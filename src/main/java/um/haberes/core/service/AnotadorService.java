/**
 * 
 */
package um.haberes.core.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import um.haberes.core.exception.AnotadorException;
import um.haberes.core.kotlin.model.Anotador;
import um.haberes.core.kotlin.model.Persona;
import um.haberes.core.repository.AnotadorRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class AnotadorService {

	private final AnotadorRepository repository;
	private final PersonaService personaservice;

	public AnotadorService(AnotadorRepository repository,
						   PersonaService personaservice) {
		this.repository = repository;
		this.personaservice = personaservice;
	}

	public List<Anotador> findAllByLegajo(Long legajoId) {
		return repository.findAllByLegajoIdOrderByAnotadorIdDesc(legajoId);
	}

	public List<Anotador> findPendientes(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndAutorizadoAndRechazadoOrderByPersonaApellidoAscPersonaNombreAsc(
			anho, mes, (byte) 0, (byte) 0);
	}

	public List<Anotador> findPendientesFiltro(Integer anho, Integer mes, String filtro) {
		List<Long> legajos = personaservice.findAllByFiltro(filtro).stream()
				.map(Persona::getLegajoId)
				.collect(Collectors.toList());
		log.debug("Legajos -> {}", legajos);
		return repository.findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndLegajoIdInOrderByPersonaApellidoAscPersonaNombreAsc(
				anho, mes, (byte) 0, (byte) 0, legajos);
	}

	public List<Anotador> findPendientesByFacultad(Integer facultadId, Integer anho, Integer mes) {
		return repository.findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndFacultadIdOrderByPersonaApellidoAscPersonaNombreAsc(
			anho, mes, (byte) 0, (byte) 0, facultadId);
	}

	public List<Anotador> findAutorizadosByFacultad(Integer facultadId, Integer anho, Integer mes) {
		return repository.findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndFacultadIdOrderByPersonaApellidoAscPersonaNombreAsc(
			anho, mes, (byte) 1, (byte) 0, facultadId);
	}

	public List<Anotador> findRechazadosByFacultad(Integer facultadId, Integer anho, Integer mes) {
		return repository.findTop1000ByAnhoAndMesAndAutorizadoAndRechazadoAndFacultadIdOrderByPersonaApellidoAscPersonaNombreAsc(
			anho, mes, (byte) 0, (byte) 1, facultadId);
	}

	public List<Anotador> findRevisados(Integer anho, Integer mes) {
		return repository.findTop1000ByAnhoAndMesOrderByPersonaApellidoAscPersonaNombreAsc(anho, mes).stream()
				.filter(anotador -> anotador.getAutorizado() == 1 || anotador.getRechazado() == 1)
				.collect(Collectors.toList());
	}

	public List<Anotador> findRevisadosFiltro(Integer anho, Integer mes, String filtro) {
		List<Long> legajos = personaservice.findAllByFiltro(filtro).stream()
				.map(Persona::getLegajoId)
				.collect(Collectors.toList());
		return repository.findTop1000ByAnhoAndMesAndLegajoIdInOrderByPersonaApellidoAscPersonaNombreAsc(anho, mes, legajos).stream()
				.filter(anotador -> anotador.getAutorizado() == 1 || anotador.getRechazado() == 1)
				.collect(Collectors.toList());
	}

	public List<Anotador> findRevisadosByFacultad(Integer facultadId, Integer anho, Integer mes) {
		return repository.findTop1000ByAnhoAndMesAndFacultadIdOrderByPersonaApellidoAscPersonaNombreAsc(anho, mes, facultadId).stream()
				.filter(anotador -> anotador.getAutorizado() == 1 || anotador.getRechazado() == 1)
				.collect(Collectors.toList());
	}

	public Anotador findByAnotadorId(Long anotadorId) {
		return repository.findByAnotadorId(anotadorId).orElseThrow(() -> new AnotadorException(anotadorId));
	}

	public Anotador add(Anotador anotador) {
		repository.save(anotador);
		return anotador;
	}

	public Anotador update(Anotador newAnotador, Long anotadorId) {
		return repository.findByAnotadorId(anotadorId).map(anotador -> {
			anotador = new Anotador(anotadorId, newAnotador.getLegajoId(), newAnotador.getAnho(), newAnotador.getMes(),
					newAnotador.getFacultadId(), newAnotador.getAnotacion(), newAnotador.getVisado(),
					newAnotador.getIpVisado(), newAnotador.getUser(), newAnotador.getRespuesta(),
					newAnotador.getAutorizado(), newAnotador.getRechazado(), newAnotador.getRectorado(),
					newAnotador.getTransferido(), newAnotador.getPersona(), newAnotador.getFacultad());
			repository.save(anotador);
			return anotador;
		}).orElseThrow(() -> new AnotadorException(anotadorId));
	}

}
