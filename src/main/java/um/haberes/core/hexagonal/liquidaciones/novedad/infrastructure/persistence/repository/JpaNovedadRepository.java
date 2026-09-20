/**
 *
 */
package um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.persistence.entity.NovedadEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaNovedadRepository extends JpaRepository<NovedadEntity, Long> {

    List<NovedadEntity> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    List<NovedadEntity> findAllByCodigoIdAndAnhoAndMes(Integer codigoId, Integer anho, Integer mes, Sort sort);

    List<NovedadEntity> findAllByImportadoAndAnhoAndMes(Byte importado, Integer anho, Integer mes);

    List<NovedadEntity> findAllByLegajoIdAndAnhoAndMesAndCodigoId(Long legajoId, Integer anho, Integer mes,
                                                            Integer codigoId);

    Optional<NovedadEntity> findByNovedadId(Long novedadId);

    Optional<NovedadEntity> findByLegajoIdAndAnhoAndMesAndCodigoIdAndDependenciaId(Long legajoId, Integer anho,
                                                                             Integer mes, Integer codigoId, Integer dependenciaId);

    Optional<NovedadEntity> findByLegajoIdAndAnhoAndMesAndCodigoIdAndDependenciaIdIsNull(Long legajoId, Integer anho, Integer mes, Integer codigoId);

    @Modifying
    public void deleteAllByAnhoAndMes(Integer anho, Integer mes);

}
