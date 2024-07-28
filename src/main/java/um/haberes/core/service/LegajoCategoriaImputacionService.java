/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.LegajoCategoriaImputacion;
import um.haberes.core.repository.ILegajoCategoriaImputacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class LegajoCategoriaImputacionService {

	@Autowired
	private ILegajoCategoriaImputacionRepository repository;

	public List<LegajoCategoriaImputacion> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	public LegajoCategoriaImputacion add(LegajoCategoriaImputacion legajocategoriaimputacion) {
		repository.save(legajocategoriaimputacion);
		return legajocategoriaimputacion;
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
