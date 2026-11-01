/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.BonoImpresionEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaBonoImpresionRepository extends JpaRepository<BonoImpresionEntity, Long> {

	List<BonoImpresionEntity> findAllByLegajoIdAndAnhoAndMesOrderByFechaDesc(Long legajoId, Integer anho,
			Integer mes);

}
