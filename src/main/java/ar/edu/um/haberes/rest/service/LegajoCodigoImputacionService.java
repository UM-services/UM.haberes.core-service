/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.LegajoCodigoImputacion;
import ar.edu.um.haberes.rest.repository.ILegajoCodigoImputacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class LegajoCodigoImputacionService {

	@Autowired
	private ILegajoCodigoImputacionRepository repository;

	public List<LegajoCodigoImputacion> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	public LegajoCodigoImputacion add(LegajoCodigoImputacion legajocodigoimputacion) {
		repository.save(legajocodigoimputacion);
		return legajocodigoimputacion;
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
