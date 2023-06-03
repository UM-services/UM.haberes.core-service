/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.CategoriaImputacionNotFoundException;
import um.haberes.rest.kotlin.model.CategoriaImputacion;
import um.haberes.rest.repository.ICategoriaImputacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class CategoriaImputacionService {

	@Autowired
	private ICategoriaImputacionRepository repository;

	public List<CategoriaImputacion> findAll() {
		return repository.findAll();
	}

	public CategoriaImputacion findByCategoriaimputacionId(Long categoriaImputacionId) {
		return repository.findByCategoriaImputacionId(categoriaImputacionId)
				.orElseThrow(() -> new CategoriaImputacionNotFoundException(categoriaImputacionId));
	}

	public CategoriaImputacion findByUnique(Integer dependenciaId, Integer facultadId, Integer geograficaId,
			Integer categoriaId) {
		return repository
				.findByDependenciaIdAndFacultadIdAndGeograficaIdAndCategoriaId(dependenciaId, facultadId, geograficaId,
						categoriaId)
				.orElseThrow(() -> new CategoriaImputacionNotFoundException(dependenciaId, facultadId, geograficaId,
						categoriaId));
	}

	public CategoriaImputacion add(CategoriaImputacion categoriaImputacion) {
		categoriaImputacion = repository.save(categoriaImputacion);
		return categoriaImputacion;
	}

	public CategoriaImputacion update(CategoriaImputacion newCategoriaImputacion, Long categoriaImputacionId) {
		return repository.findByCategoriaImputacionId(categoriaImputacionId).map(categoriaImputacion -> {
			categoriaImputacion = new CategoriaImputacion(categoriaImputacionId,
					newCategoriaImputacion.getDependenciaId(), newCategoriaImputacion.getFacultadId(),
					newCategoriaImputacion.getGeograficaId(), newCategoriaImputacion.getCategoriaId(),
					newCategoriaImputacion.getCuentaSueldos(), newCategoriaImputacion.getCuentaAportes());
			repository.save(categoriaImputacion);
			return categoriaImputacion;
		}).orElseThrow(() -> new CategoriaImputacionNotFoundException(categoriaImputacionId));
	}

}
