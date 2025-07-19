/**
 *
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.CargoClaseDetalle;

/**
 * @author daniel
 *
 */
@Repository
public interface CargoClaseDetalleRepository extends JpaRepository<CargoClaseDetalle, Long> {

    List<CargoClaseDetalle> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    List<CargoClaseDetalle> findAllByLegajoIdAndAnhoAndMesAndFacultadId(Long legajoId, Integer anho, Integer mes, Integer facultadId);

    List<CargoClaseDetalle> findAllByCargoClaseDetalleIdIn(List<Long> cargoClaseDetalleIds);

    List<CargoClaseDetalle> findAllByFacultadIdAndAnhoAndMes(Integer facultadId, Integer anho, Integer mes,
                                                             Sort sort);

    List<CargoClaseDetalle> findAllByCargoClasePeriodoIdOrderByCargoClaseDetalleId(Long cargoClasePeriodoId);

    List<CargoClaseDetalle> findAllByCargoClaseIdAndAnhoAndMesOrderByLegajoId(Long cargoClaseId, Integer anho,
                                                                              Integer mes);

    Optional<CargoClaseDetalle> findByCargoClaseDetalleId(Long cargoClasePeriodoId);

    @Modifying
    void deleteByCargoClaseDetalleId(Long cargoClasePeriodoId);

    @Modifying
    void deleteAllByCargoClasePeriodoId(Long cargoClasePeriodoId);

}
