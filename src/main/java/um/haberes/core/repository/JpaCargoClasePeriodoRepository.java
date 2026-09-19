/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.CargoClasePeriodoEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCargoClasePeriodoRepository extends JpaRepository<CargoClasePeriodoEntity, Long> {

	public List<CargoClasePeriodoEntity> findAllByFacultadIdOrderByLegajoId(Integer facultadId);

	public List<CargoClasePeriodoEntity> findAllByLegajoIdOrderByCargoClasePeriodoIdDesc(Long legajoId);

	public Optional<CargoClasePeriodoEntity> findByCargoClasePeriodoId(Long cargoClasePeriodoId);

	@Modifying
	public void deleteByCargoClasePeriodoId(Long cargoClasePeriodoId);

}
