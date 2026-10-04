/**
 *
 */
package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.entity.CargoLiquidacionEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCargoLiquidacionRepository extends JpaRepository<CargoLiquidacionEntity, Long>,
        CargoLiquidacionRepositoryCustom {

    List<CargoLiquidacionEntity> findAllByLegajoIdAndAnhoAndMesAndDependenciaFacultadIdAndCategoriaDocente(Long legajoId, Integer anho, Integer mes, Integer facultadId, Byte docente);

    List<CargoLiquidacionEntity> findAllByLegajoIdAndAnhoAndMesAndDependenciaFacultadIdAndCategoriaNoDocente(Long legajoId, Integer anho, Integer mes, Integer facultadId, Byte noDocente);

    List<CargoLiquidacionEntity> findAllByLegajoIdAndAnhoAndMesAndCategoriaDocente(Long legajoId, Integer anho,
            Integer mes, Byte docente);

    List<CargoLiquidacionEntity> findAllByLegajoIdAndAnhoAndMesAndCategoriaNoDocente(Long legajoId, Integer anho,
            Integer mes, Byte noDocente);

    List<CargoLiquidacionEntity> findAllByLegajoIdAndAnhoAndMesAndSituacion(Long legajoId, Integer anho, Integer mes,
                                                                      String situacion);

    List<CargoLiquidacionEntity> findAllByAnhoAndMesAndSituacion(Integer anho, Integer mes, String situacion,
                                                                 Sort sort);

    List<CargoLiquidacionEntity> findAllByLegajoIdInAndCategoriaIdInAndAnhoAndMes(List<Long> legajos, List<Integer> categoriaIds,
                                                                            Integer anho, Integer mes);

    List<CargoLiquidacionEntity> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdIn(Long legajoId, Integer anho, Integer mes,
                                                                          List<Integer> categorias);

    List<CargoLiquidacionEntity> findAllByLegajoIdAndCategoriaIdInAndCategoriaBasicoGreaterThan(Long legajoId, List<Integer> categoriaIds,
                                                                                          BigDecimal value);

    List<CargoLiquidacionEntity> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdNotIn(Long legajoId, Integer anho, Integer mes,
                                                                             List<Integer> categoriaIds);

    List<CargoLiquidacionEntity> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdBetween(Long legajoId, Integer anho,
                                                                               Integer mes, Integer categoriaIdDesde, Integer categoriaIdHasta);

    Optional<CargoLiquidacionEntity> findByLegajoIdAndAnhoAndMesAndCategoriaId(Long legajoId, Integer anho, Integer mes,
                                                                         Integer categoriaId);

    Optional<CargoLiquidacionEntity> findByCargoLiquidacionId(Long cargoLiquidacionId);

    @Modifying
    void deleteAllByLegajoIdNotInAndAnhoAndMes(List<Long> legajos, Integer anho, Integer mes);

    @Modifying
    void deleteAllByAnhoAndMes(Integer anho, Integer mes);

    @Modifying
    void deleteAllByLegajoIdAndAnhoAndMesAndSituacionAndCategoriaIdIn(Long legajoId, Integer anho, Integer mes,
                                                                      String string, List<Integer> categoriaIds);

}
