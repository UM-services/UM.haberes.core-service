package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;

public interface LiquidacionRepository {

    Liquidacion save(Liquidacion liquidacion);

    List<Liquidacion> saveAll(List<Liquidacion> liquidaciones);

    Optional<Liquidacion> findByLiquidacionId(Long liquidacionId);

    Optional<Liquidacion> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    List<Liquidacion> findAllByAnhoAndMes(Integer anho, Integer mes, Integer limit);

    List<Liquidacion> findAllByAnhoAndMesAndLegajoId(Integer anho, Integer mes, Long legajoId, Integer limit);

    List<Liquidacion> findAllByAnhoAndMesAndLegajoIdIn(Integer anho, Integer mes, List<Long> legajoIds);

    List<Liquidacion> findAllByAnhoAndMesBetween(Integer anho, Integer mesDesde, Integer mesHasta);

    List<Liquidacion> findAllByAnhoAndMesBetweenOrderByLegajoId(Integer anho, Integer mesDesde, Integer mesHasta, Integer limit);

    List<Liquidacion> findAllByAnhoAndMesBetweenAndLegajoId(Integer anho, Integer mesDesde, Integer mesHasta, Long legajoId, Integer limit);

    List<Liquidacion> findAllByLegajoIdInAndAnhoAndMes(List<Long> legajoIds, Integer anho, Integer mes);

    List<Liquidacion> findAllByLegajoId(Long legajoId);

    List<Liquidacion> findAllByDependenciaIdAndAnhoAndMesAndSalida(Integer dependenciaId, Integer anho, Integer mes, String salida);

    List<Liquidacion> findAllByAnhoAndMesAndFechaAcreditacionNotNull(Integer anho, Integer mes);

    List<Liquidacion> findAllByAnhoAndMesAndFechaAcreditacionNotNullAndLegajoIdIn(Integer anho, Integer mes, List<Long> legajoIds);

    void deleteAllByAnhoAndMes(Integer anho, Integer mes);

    void deleteByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);
}
