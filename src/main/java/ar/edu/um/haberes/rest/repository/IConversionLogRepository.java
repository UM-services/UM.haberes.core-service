/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.um.haberes.rest.model.ConversionLog;

/**
 * @author daniel
 *
 */
public interface IConversionLogRepository extends JpaRepository<ConversionLog, Long> {

}
