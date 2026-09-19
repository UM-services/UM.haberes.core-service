package um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;

public interface NovedadRepository {

    Novedad save(Novedad novedad);

    List<Novedad> saveAll(List<Novedad> novedades);

    Optional<Novedad> findByNovedadId(Long novedadId);

    List<Novedad> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    List<Novedad> findAllByCodigoIdAndAnhoAndMes(Integer codigoId, Integer anho, Integer mes);

    List<Novedad> findAllByImportadoAndAnhoAndMes(Byte importado, Integer anho, Integer mes);

    List<Novedad> findAllByLegajoIdAndAnhoAndMesAndCodigoId(Long legajoId, Integer anho, Integer mes,
            Integer codigoId);

    Optional<Novedad> findByLegajoIdAndAnhoAndMesAndCodigoIdAndDependenciaId(Long legajoId, Integer anho, Integer mes,
            Integer codigoId, Integer dependenciaId);

    Optional<Novedad> findByLegajoIdAndAnhoAndMesAndCodigoIdAndDependenciaIdIsNull(Long legajoId, Integer anho,
            Integer mes, Integer codigoId);

    void deleteAllByAnhoAndMes(Integer anho, Integer mes);

    void deleteByNovedadId(Long novedadId);

}
