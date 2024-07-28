/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.CargoClasePeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface ICargoClasePeriodoRepository extends JpaRepository<CargoClasePeriodo, Long> {

	public List<CargoClasePeriodo> findAllByFacultadIdOrderByLegajoId(Integer facultadId);

	public List<CargoClasePeriodo> findAllByLegajoIdOrderByCargoClasePeriodoIdDesc(Long legajoId);

	public Optional<CargoClasePeriodo> findByCargoClasePeriodoId(Long cargoClasePeriodoId);

	@Modifying
	public void deleteByCargoClasePeriodoId(Long cargoClasePeriodoId);

}
