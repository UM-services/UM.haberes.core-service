/**
 * 
 */
package um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.rest.kotlin.model.CargoClaseDetalle;

/**
 * @author daniel
 *
 */
@Repository
public interface ICargoClaseDetalleRepository extends JpaRepository<CargoClaseDetalle, Long> {

	public List<CargoClaseDetalle> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	public List<CargoClaseDetalle> findAllByCargoClaseDetalleIdIn(List<Long> cargoClaseDetalleIds);

	public List<CargoClaseDetalle> findAllByFacultadIdAndAnhoAndMes(Integer facultadId, Integer anho, Integer mes,
			Sort sort);

	public List<CargoClaseDetalle> findAllByCargoClasePeriodoIdOrderByCargoClaseDetalleId(Long cargoClasePeriodoId);

	public List<CargoClaseDetalle> findAllByCargoClaseIdAndAnhoAndMesOrderByLegajoId(Long cargoClaseId, Integer anho,
			Integer mes);

	public Optional<CargoClaseDetalle> findByCargoClaseDetalleId(Long cargoClasePeriodoId);

	@Modifying
	public void deleteByCargoClaseDetalleId(Long cargoClasePeriodoId);

	@Modifying
	public void deleteAllByCargoClasePeriodoId(Long cargoClasePeriodoId);

}
