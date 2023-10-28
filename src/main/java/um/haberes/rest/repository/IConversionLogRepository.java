/**
 * 
 */
package um.haberes.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.rest.kotlin.model.ConversionLog;

/**
 * @author daniel
 *
 */
public interface IConversionLogRepository extends JpaRepository<ConversionLog, Long> {

}
