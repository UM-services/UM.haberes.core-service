/**
 * 
 */
package um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.persistence.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.persistence.entity.DesignacionTipoEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaDesignacionTipoRepository extends JpaRepository<DesignacionTipoEntity, Integer> {

	public Optional<DesignacionTipoEntity> findFirstByHorasSemanalesGreaterThanEqual(BigDecimal horasSemanales);

	public Optional<DesignacionTipoEntity> findByDesignacionTipoId(Integer designacionTipoId);

}
