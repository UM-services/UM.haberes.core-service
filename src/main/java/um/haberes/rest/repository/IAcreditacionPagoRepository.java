/**
 * 
 */
package um.haberes.rest.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.rest.kotlin.model.AcreditacionPago;

/**
 * @author daniel
 *
 */
public interface IAcreditacionPagoRepository extends JpaRepository<AcreditacionPago, Long> {

	public Optional<AcreditacionPago> findByAnhoAndMesAndFechaPago(Integer anho, Integer mes, OffsetDateTime fechaPago);

}
