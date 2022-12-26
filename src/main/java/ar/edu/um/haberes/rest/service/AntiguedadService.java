/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.time.OffsetDateTime;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.AntiguedadNotFoundException;
import ar.edu.um.haberes.rest.model.Antiguedad;
import ar.edu.um.haberes.rest.model.Persona;
import ar.edu.um.haberes.rest.model.view.AntiguedadPeriodo;
import ar.edu.um.haberes.rest.repository.IAntiguedadRepository;
import ar.edu.um.haberes.rest.service.view.AntiguedadPeriodoService;
import ar.edu.um.haberes.rest.util.Periodo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class AntiguedadService {

	@Autowired
	private IAntiguedadRepository repository;

	@Autowired
	private AntiguedadPeriodoService antiguedadPeriodoService;

	@Autowired
	private PersonaService personaService;

	public List<Antiguedad> findAllByPeriodo(Integer anho, Integer mes, Integer limit) {
		return repository.findAllByAnhoAndMes(anho, mes, PageRequest.of(0, limit));
	}

	public Antiguedad findByUnique(Long legajoId, Integer anho, Integer mes) {
		return repository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes)
				.orElseThrow(() -> new AntiguedadNotFoundException(legajoId, anho, mes));
	}

	public AntiguedadPeriodo findLastByUnique(Long legajoId, Integer anho, Integer mes) {
		return antiguedadPeriodoService.findLastByUnique(legajoId, anho, mes);
	}

	public Antiguedad add(Antiguedad antiguedad) {
		repository.save(antiguedad);
		log.debug(antiguedad.toString());
		return antiguedad;
	}

	public Antiguedad update(Antiguedad newAntiguedad, Long antiguedadId) {
		return repository.findByAntiguedadId(antiguedadId).map(antiguedad -> {
			antiguedad = new Antiguedad(antiguedadId, newAntiguedad.getLegajoId(), newAntiguedad.getAnho(),
					newAntiguedad.getMes(), newAntiguedad.getMesesDocentes(), newAntiguedad.getMesesAdministrativos(),
					newAntiguedad.getPersona());
			antiguedad = repository.save(antiguedad);
			return antiguedad;
		}).orElseThrow(() -> new AntiguedadNotFoundException(antiguedadId));
	}

	@Transactional
	public List<Antiguedad> saveAll(List<Antiguedad> antiguedades) {
		repository.saveAll(antiguedades);
		return antiguedades;
	}

	@Transactional
	public void calculate(Long legajoId, Integer anho, Integer mes) {
		Persona persona = personaService.findByLegajoId(legajoId);
		Periodo ingresoDocente = null;
		Periodo ingresoAdministrativo = null;
		Integer mesesDocentes = 0;
		Integer mesesAdministrativos = 0;
		if (persona.getAltaDocente() != null) {
			OffsetDateTime alta = persona.getAltaDocente().plusHours(3);
			ingresoDocente = new Periodo(alta.getYear(), alta.getMonthValue());
			mesesDocentes = persona.getAjusteDocente() + Periodo.diffMonth(ingresoDocente, new Periodo(anho, mes));
		}
		if (persona.getAltaAdministrativa() != null) {
			OffsetDateTime alta = persona.getAltaDocente().plusHours(3);
			ingresoAdministrativo = new Periodo(alta.getYear(), alta.getMonthValue());
			mesesAdministrativos = persona.getAjusteAdministrativo()
					+ Periodo.diffMonth(ingresoAdministrativo, new Periodo(anho, mes));
		}
		Long antiguedadId = null;
		Antiguedad antiguedad = null;
		try {
			antiguedad = this.findByUnique(legajoId, anho, mes);
			antiguedadId = antiguedad.getAntiguedadId();
		} catch (AntiguedadNotFoundException e) {
		}
		antiguedad = new Antiguedad(antiguedadId, legajoId, anho, mes, mesesDocentes, mesesAdministrativos, null);
		if (antiguedadId == null) {
			antiguedad = this.add(antiguedad);
		} else {
			antiguedad = this.update(antiguedad, antiguedadId);
		}
		log.debug("Antiguedad -> {}", antiguedad);
	}

}
