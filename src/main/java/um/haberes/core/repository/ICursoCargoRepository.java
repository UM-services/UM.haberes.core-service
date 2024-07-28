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
import um.haberes.core.kotlin.model.CursoCargo;

/**
 * @author daniel
 *
 */
@Repository
public interface ICursoCargoRepository extends JpaRepository<CursoCargo, Long> {

    List<CursoCargo> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes, Sort sort);

    List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndDesarraigo(Long legajoId, Integer anho, Integer mes,
                                                                 Byte desarraigo);

    List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndCargoTipoId(Long legajoId, Integer anho,
                                                                  Integer mes, Integer cargoTipoId);

    List<CursoCargo> findAllByCursoIdAndAnhoAndMes(Long cursoId, Integer anho, Integer mes, Sort sort);

    List<CursoCargo> findTopByCursoId(Long cursoId);

    List<CursoCargo> findAllByAnhoAndMes(Integer anho, Integer mes);

    List<CursoCargo> findTopByAnhoAndMes(Integer anho, Integer mes);

    List<CursoCargo> findAllByAnhoAndMesAndDesarraigo(Integer anho, Integer mes, Byte desarraigo);

    List<CursoCargo> findAllByCursoIdIn(List<Long> cursoIds);

    List<CursoCargo> findAllByLegajoIdAndAnhoAndMesAndCursoFacultadId(Long legajoId, Integer anho, Integer mes, Integer facultadId);

    Optional<CursoCargo> findByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(Long cursoId, Integer anho,
                                                                             Integer mes, Integer cargoTipoId, Long legajoId);

    Optional<CursoCargo> findByCursoCargoId(Long cursoCargoId);

    Optional<CursoCargo> findByCursoIdAndAnhoAndMesAndLegajoId(Long cursoId, Integer anho, Integer mes,
                                                               Long legajoId);

    @Modifying
    void deleteByCursoCargoId(Long cursoCargoId);

    @Modifying
    void deleteByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(Long cursoId, Integer anho, Integer mes,
                                                               Integer cargoTipoId, Long legajoId);

}
