/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.CategoriaImputacionException;
import um.haberes.core.kotlin.model.CategoriaImputacion;
import um.haberes.core.repository.CategoriaImputacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class CategoriaImputacionService {

	@Autowired
	private CategoriaImputacionRepository repository;

	public List<CategoriaImputacion> findAll() {
		return repository.findAll();
	}

	public CategoriaImputacion findByCategoriaimputacionId(Long categoriaImputacionId) {
		return repository.findByCategoriaImputacionId(categoriaImputacionId)
				.orElseThrow(() -> new CategoriaImputacionException(categoriaImputacionId));
	}

	public CategoriaImputacion findByUnique(Integer dependenciaId, Integer facultadId, Integer geograficaId,
			Integer categoriaId) {
		return repository
				.findByDependenciaIdAndFacultadIdAndGeograficaIdAndCategoriaId(dependenciaId, facultadId, geograficaId,
						categoriaId)
				.orElseThrow(() -> new CategoriaImputacionException(dependenciaId, facultadId, geograficaId,
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
		}).orElseThrow(() -> new CategoriaImputacionException(categoriaImputacionId));
	}

}
