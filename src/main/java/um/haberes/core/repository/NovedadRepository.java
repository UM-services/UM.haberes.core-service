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
import um.haberes.core.kotlin.model.Novedad;

/**
 * @author daniel
 *
 */
@Repository
public interface NovedadRepository extends JpaRepository<Novedad, Long> {

    List<Novedad> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    List<Novedad> findAllByCodigoIdAndAnhoAndMes(Integer codigoId, Integer anho, Integer mes, Sort sort);

    List<Novedad> findAllByImportadoAndAnhoAndMes(Byte importado, Integer anho, Integer mes);

    List<Novedad> findAllByLegajoIdAndAnhoAndMesAndCodigoId(Long legajoId, Integer anho, Integer mes,
                                                            Integer codigoId);

    Optional<Novedad> findByNovedadId(Long novedadId);

    Optional<Novedad> findByLegajoIdAndAnhoAndMesAndCodigoIdAndDependenciaId(Long legajoId, Integer anho,
                                                                             Integer mes, Integer codigoId, Integer dependenciaId);

    Optional<Novedad> findByLegajoIdAndAnhoAndMesAndCodigoIdAndDependenciaIdIsNull(Long legajoId, Integer anho, Integer mes, Integer codigoId);

    @Modifying
    public void deleteAllByAnhoAndMes(Integer anho, Integer mes);

}
