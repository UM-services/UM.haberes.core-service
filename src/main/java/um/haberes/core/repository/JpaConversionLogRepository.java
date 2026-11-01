/**
 * 
 */
package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.model.ConversionLogEntity;

/**
 * @author daniel
 *
 */
public interface JpaConversionLogRepository extends JpaRepository<ConversionLogEntity, Long> {

}
