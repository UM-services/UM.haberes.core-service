/**
 * 
 */
package um.haberes.core.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.Cargo;

/**
 * @author daniel
 *
 */
@Repository
public interface ICargoRepository extends JpaRepository<Cargo, Long>{

	public List<Cargo> findAllByLegajoIdOrderByCargoIdDesc(Long legajoId);

	public List<Cargo> findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaIsNull(Long legajoId,
			OffsetDateTime firstDate);

	public List<Cargo> findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaGreaterThanEqual(Long legajoId,
			OffsetDateTime firstDate, OffsetDateTime lastDate);

	public Optional<Cargo> findByCargoId(Long cargoId);

	@Modifying
	public void deleteByCargoId(Long cargoId);

}
