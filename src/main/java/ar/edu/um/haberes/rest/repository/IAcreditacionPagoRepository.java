/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.um.haberes.rest.model.AcreditacionPago;

/**
 * @author daniel
 *
 */
public interface IAcreditacionPagoRepository extends JpaRepository<AcreditacionPago, Long> {

	public Optional<AcreditacionPago> findByAnhoAndMesAndFechaPago(Integer anho, Integer mes, OffsetDateTime fechaPago);

}
