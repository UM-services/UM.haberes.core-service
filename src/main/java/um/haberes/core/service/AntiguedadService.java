/**
 * 
 */
package um.haberes.core.service;

import java.time.OffsetDateTime;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.AntiguedadException;
import um.haberes.core.kotlin.model.Antiguedad;
import um.haberes.core.kotlin.model.Persona;
import um.haberes.core.kotlin.model.view.AntiguedadPeriodo;
import um.haberes.core.repository.AntiguedadRepository;
import um.haberes.core.service.view.AntiguedadPeriodoService;
import um.haberes.core.util.Periodo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class AntiguedadService {

	@Autowired
	private AntiguedadRepository repository;

	@Autowired
	private AntiguedadPeriodoService antiguedadPeriodoService;

	@Autowired
	private PersonaService personaService;

	public List<Antiguedad> findAllByPeriodo(Integer anho, Integer mes, Integer limit) {
		return repository.findAllByAnhoAndMes(anho, mes, PageRequest.of(0, limit));
	}

	public Antiguedad findByUnique(Long legajoId, Integer anho, Integer mes) {
		return repository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes)
				.orElseThrow(() -> new AntiguedadException(legajoId, anho, mes));
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
		}).orElseThrow(() -> new AntiguedadException(antiguedadId));
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
			ingresoDocente = Periodo.builder().anho(alta.getYear()).mes(alta.getMonthValue()).build();
			mesesDocentes = persona.getAjusteDocente() + Periodo.diffMonth(ingresoDocente, Periodo.builder().anho(anho).mes(mes).build());
		}
		if (persona.getAltaAdministrativa() != null) {
			OffsetDateTime alta = persona.getAltaDocente().plusHours(3);
			ingresoAdministrativo = Periodo.builder().anho(alta.getYear()).mes(alta.getMonthValue()).build();
			mesesAdministrativos = persona.getAjusteAdministrativo()
					+ Periodo.diffMonth(ingresoAdministrativo, Periodo.builder().anho(anho).mes(mes).build());
		}
		Long antiguedadId = null;
		Antiguedad antiguedad = null;
		try {
			antiguedad = this.findByUnique(legajoId, anho, mes);
			antiguedadId = antiguedad.getAntiguedadId();
		} catch (AntiguedadException e) {
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
