/**
 * 
 */
package um.haberes.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.CargoClaseImputacion;

/**
 * @author daniel
 *
 */
@Repository
public interface ICargoClaseImputacionRepository extends JpaRepository<CargoClaseImputacion, Long> {

	public Optional<CargoClaseImputacion> findByCargoClaseImputacionId(Long cargoClaseImputacionId);

	public Optional<CargoClaseImputacion> findByDependenciaIdAndFacultadIdAndGeograficaIdAndCargoClaseId(
			Integer dependenciaId, Integer facultadId, Integer geograficaId, Long cargoClaseId);

}
