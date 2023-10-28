/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.kotlin.model.LegajoCodigoImputacion;
import um.haberes.rest.repository.ILegajoCodigoImputacionRepository;

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

	public List<LegajoCodigoImputacion> findAllByLegajoAndCodigos(Long legajoId, Integer anho, Integer mes, List<Integer> codigoIds) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndCodigoIdIn(legajoId, anho, mes, codigoIds);
	}

	public LegajoCodigoImputacion add(LegajoCodigoImputacion legajocodigoimputacion) {
		legajocodigoimputacion = repository.save(legajocodigoimputacion);
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
