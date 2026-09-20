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
import um.haberes.core.model.CargoClaseDetalleEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCargoClaseDetalleRepository extends JpaRepository<CargoClaseDetalleEntity, Long> {

    List<CargoClaseDetalleEntity> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    List<CargoClaseDetalleEntity> findAllByLegajoIdAndAnhoAndMesAndFacultadId(Long legajoId, Integer anho, Integer mes, Integer facultadId);

    List<CargoClaseDetalleEntity> findAllByCargoClaseDetalleIdIn(List<Long> cargoClaseDetalleIds);

    List<CargoClaseDetalleEntity> findAllByFacultadIdAndAnhoAndMes(Integer facultadId, Integer anho, Integer mes,
                                                             Sort sort);

    List<CargoClaseDetalleEntity> findAllByCargoClasePeriodoIdOrderByCargoClaseDetalleId(Long cargoClasePeriodoId);

    List<CargoClaseDetalleEntity> findAllByCargoClaseIdAndAnhoAndMesOrderByLegajoId(Long cargoClaseId, Integer anho,
                                                                              Integer mes);

    Optional<CargoClaseDetalleEntity> findByCargoClaseDetalleId(Long cargoClasePeriodoId);

    @Modifying
    void deleteByCargoClaseDetalleId(Long cargoClasePeriodoId);

    @Modifying
    void deleteAllByCargoClasePeriodoId(Long cargoClasePeriodoId);

}
