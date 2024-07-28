/**
 * 
 */
package um.haberes.core.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.DesignacionTipo;

/**
 * @author daniel
 *
 */
@Repository
public interface IDesignacionTipoRepository extends JpaRepository<DesignacionTipo, Integer> {

	public Optional<DesignacionTipo> findFirstByHorasSemanalesGreaterThanEqual(BigDecimal horasSemanales);

	public Optional<DesignacionTipo> findByDesignacionTipoId(Integer designacionTipoId);

}
