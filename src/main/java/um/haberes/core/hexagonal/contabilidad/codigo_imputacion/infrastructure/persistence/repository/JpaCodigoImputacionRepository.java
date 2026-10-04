/**
 * 
 */
package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.persistence.entity.CodigoImputacionEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCodigoImputacionRepository extends JpaRepository<CodigoImputacionEntity, Long>{

	Optional<CodigoImputacionEntity> findByCodigoImputacionId(Long codigoImputacionId);

	Optional<CodigoImputacionEntity> findByDependenciaIdAndFacultadIdAndGeograficaIdAndCodigoId(Integer dependenciaId,
			Integer facultadId, Integer geograficaId, Integer codigoId);

}
