/**
 * 
 */
package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.kotlin.model.ConversionLog;

/**
 * @author daniel
 *
 */
public interface ConversionLogRepository extends JpaRepository<ConversionLog, Long> {

}
