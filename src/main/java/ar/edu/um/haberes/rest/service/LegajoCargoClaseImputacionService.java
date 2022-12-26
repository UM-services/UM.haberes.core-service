/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.LegajoCargoClaseImputacion;
import ar.edu.um.haberes.rest.repository.ILegajoCargoClaseImputacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class LegajoCargoClaseImputacionService {

	@Autowired
	private ILegajoCargoClaseImputacionRepository repository;

	public List<LegajoCargoClaseImputacion> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	public LegajoCargoClaseImputacion add(LegajoCargoClaseImputacion legajocargoclaseimputacion) {
		repository.save(legajocargoclaseimputacion);
		return legajocargoclaseimputacion;
	}

	@Transactional
	public void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes) {
		repository.deleteAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	@Transactional
	public void deleteAllByPeriodo(Integer anho, Integer mes) {
		repository.deleteAllByAnhoAndMes(anho, mes);
	}

}
