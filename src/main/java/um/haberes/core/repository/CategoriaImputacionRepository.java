/**
 * 
 */
package um.haberes.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.CategoriaImputacion;

/**
 * @author daniel
 *
 */
@Repository
public interface CategoriaImputacionRepository extends JpaRepository<CategoriaImputacion, Long> {

	public Optional<CategoriaImputacion> findByCategoriaImputacionId(Long categoriaImputacionId);

	public Optional<CategoriaImputacion> findByDependenciaIdAndFacultadIdAndGeograficaIdAndCategoriaId(
			Integer dependenciaId, Integer facultadId, Integer geograficaId, Integer categoriaId);

}
