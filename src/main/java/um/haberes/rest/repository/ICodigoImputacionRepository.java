/**
 * 
 */
package um.haberes.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.rest.kotlin.model.CodigoImputacion;

/**
 * @author daniel
 *
 */
@Repository
public interface ICodigoImputacionRepository extends JpaRepository<CodigoImputacion, Long>{

	public Optional<CodigoImputacion> findByCodigoImputacionId(Long codigoImputacionId);

	public Optional<CodigoImputacion> findByDependenciaIdAndFacultadIdAndGeograficaIdAndCodigoId(Integer dependenciaId,
			Integer facultadId, Integer geograficaId, Integer codigoId);

}
