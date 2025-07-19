/**
 * 
 */
package um.haberes.core.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.kotlin.model.AcreditacionPago;

/**
 * @author daniel
 *
 */
public interface AcreditacionPagoRepository extends JpaRepository<AcreditacionPago, Long> {

	public Optional<AcreditacionPago> findByAnhoAndMesAndFechaPago(Integer anho, Integer mes, OffsetDateTime fechaPago);

}
