/**
 *
 */
package um.haberes.core.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import um.haberes.core.kotlin.model.CargoLiquidacion;

/**
 * @author daniel
 *
 */
@Repository
public interface CargoLiquidacionRepository extends JpaRepository<CargoLiquidacion, Long> {

    @Query("SELECT cl FROM CargoLiquidacion cl JOIN FETCH cl.categoria WHERE cl.legajoId = :legajoId AND cl.anho = :anho AND cl.mes = :mes")
    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndDependenciaFacultadIdAndCategoriaDocente(Long legajoId, Integer anho, Integer mes, Integer facultadId, Byte docente);

    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndDependenciaFacultadIdAndCategoriaNoDocente(Long legajoId, Integer anho, Integer mes, Integer facultadId, Byte noDocente);

    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndSituacion(Long legajoId, Integer anho, Integer mes,
                                                                      String situacion);

    List<CargoLiquidacion> findAllByAnhoAndMes(Integer anho, Integer mes, Sort sort);

    List<CargoLiquidacion> findAllByAnhoAndMesAndSituacion(Integer anho, Integer mes, String situacion, Sort sort);

    List<CargoLiquidacion> findAllByLegajoIdInAndCategoriaIdInAndAnhoAndMes(List<Long> legajos, List<Integer> categoriaIds,
                                                                            Integer anho, Integer mes);

    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdIn(Long legajoId, Integer anho, Integer mes,
                                                                          List<Integer> categorias);

    List<CargoLiquidacion> findAllByLegajoIdAndCategoriaIdInAndCategoriaBasicoGreaterThan(Long legajoId, List<Integer> categoriaIds,
                                                                                          BigDecimal value);

    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdNotIn(Long legajoId, Integer anho, Integer mes,
                                                                             List<Integer> categoriaIds);

    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdBetween(Long legajoId, Integer anho,
                                                                               Integer mes, Integer categoriaIdDesde, Integer categoriaIdHasta);

    Optional<CargoLiquidacion> findByLegajoIdAndAnhoAndMesAndCategoriaId(Long legajoId, Integer anho, Integer mes,
                                                                         Integer categoriaId);

    Optional<CargoLiquidacion> findByCargoLiquidacionId(Long cargoLiquidacionId);

    @Modifying
    public void deleteAllByLegajoIdNotInAndAnhoAndMes(List<Long> legajos, Integer anho, Integer mes);

    @Modifying
    void deleteAllByAnhoAndMes(Integer anho, Integer mes);

    @Modifying
    void deleteAllByLegajoIdAndAnhoAndMesAndSituacionAndCategoriaIdIn(Long legajoId, Integer anho, Integer mes,
                                                                      String string, List<Integer> categoriaIds);

}
