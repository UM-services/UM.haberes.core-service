package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;

public interface CargoLiquidacionRepository {

    CargoLiquidacion save(CargoLiquidacion cargoLiquidacion);

    List<CargoLiquidacion> saveAll(List<CargoLiquidacion> cargos);

    Optional<CargoLiquidacion> findByCargoLiquidacionId(Long cargoLiquidacionId);

    Optional<CargoLiquidacion> findByLegajoIdAndAnhoAndMesAndCategoriaId(Long legajoId, Integer anho, Integer mes,
            Integer categoriaId);

    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaDocente(Long legajoId, Integer anho, Integer mes,
            Byte docente);

    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaNoDocente(Long legajoId, Integer anho,
            Integer mes, Byte noDocente);

    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndDependenciaFacultadIdAndCategoriaDocente(Long legajoId,
            Integer anho, Integer mes, Integer facultadId, Byte docente);

    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndDependenciaFacultadIdAndCategoriaNoDocente(Long legajoId,
            Integer anho, Integer mes, Integer facultadId, Byte noDocente);

    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdBetween(Long legajoId, Integer anho,
            Integer mes, Integer categoriaIdDesde, Integer categoriaIdHasta);

    List<CargoLiquidacion> findAllByLegajoIdAndCategoriaIdInAndCategoriaBasicoGreaterThan(Long legajoId,
            List<Integer> categoriaIds, BigDecimal basico);

    List<CargoLiquidacion> findAllByAnhoAndMesAndSituacion(Integer anho, Integer mes, String situacion);

    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndSituacion(Long legajoId, Integer anho, Integer mes,
            String situacion);

    List<CargoLiquidacion> findAllByLegajoIdInAndCategoriaIdInAndAnhoAndMes(List<Long> legajoIds,
            List<Integer> categoriaIds, Integer anho, Integer mes);

    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdIn(Long legajoId, Integer anho, Integer mes,
            List<Integer> categoriaIds);

    List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdNotIn(Long legajoId, Integer anho, Integer mes,
            List<Integer> categoriaIds);

    void deleteAllByLegajoIdNotInAndAnhoAndMes(List<Long> legajoIds, Integer anho, Integer mes);

    void deleteAllByAnhoAndMes(Integer anho, Integer mes);

    void deleteAllByLegajoIdAndAnhoAndMesAndSituacionAndCategoriaIdIn(Long legajoId, Integer anho, Integer mes,
            String situacion, List<Integer> categoriaIds);
}
