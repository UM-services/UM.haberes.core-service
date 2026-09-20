/**
 *
 */
package um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.persistence.entity.CursoCargoEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCursoCargoRepository extends JpaRepository<CursoCargoEntity, Long> {

    List<CursoCargoEntity> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes, Sort sort);

    List<CursoCargoEntity> findAllByLegajoIdAndAnhoAndMesAndDesarraigo(Long legajoId, Integer anho, Integer mes,
                                                                 Byte desarraigo);

    List<CursoCargoEntity> findAllByLegajoIdAndAnhoAndMesAndCargoTipoId(Long legajoId, Integer anho,
                                                                  Integer mes, Integer cargoTipoId);

    List<CursoCargoEntity> findAllByCursoIdAndAnhoAndMes(Long cursoId, Integer anho, Integer mes, Sort sort);

    List<CursoCargoEntity> findTopByCursoId(Long cursoId);

    List<CursoCargoEntity> findAllByAnhoAndMes(Integer anho, Integer mes);

    List<CursoCargoEntity> findTopByAnhoAndMes(Integer anho, Integer mes);

    List<CursoCargoEntity> findAllByAnhoAndMesAndDesarraigo(Integer anho, Integer mes, Byte desarraigo);

    List<CursoCargoEntity> findAllByCursoIdIn(List<Long> cursoIds);

    List<CursoCargoEntity> findAllByLegajoIdAndAnhoAndMesAndCursoFacultadId(Long legajoId, Integer anho, Integer mes, Integer facultadId);

    List<CursoCargoEntity> findAllByLegajoIdAndAnhoAndMesAndCursoNivelId(Long legajoId, Integer anho, Integer mes, Integer nivelId, Sort sort);

    List<CursoCargoEntity> findAllByLegajoIdAndAnhoAndMesAndCursoNivelIdIn(Long legajoId, Integer anho, Integer mes, List<Integer> nivelIds, Sort sort);

    List<CursoCargoEntity> findAllByLegajoIdAndAnhoAndMesAndCursoAdicionalCargaHoraria(Long legajoId, Integer anho, Integer mes, Byte adicionalCargaHoraria, Sort sort);

    List<CursoCargoEntity> findAllByLegajoIdAndAnhoAndMesAndCargoTipoIdAndCursoFacultadIdAndCursoGeograficaIdAndCursoAnualAndCursoSemestre1AndCursoSemestre2(Long legajoId, Integer anho, Integer mes, Integer cargoTipoId, Integer facultadId, Integer geograficaId, Byte anual, Byte semestre1, Byte semestre2);

    Optional<CursoCargoEntity> findByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(Long cursoId, Integer anho,
                                                                             Integer mes, Integer cargoTipoId, Long legajoId);

    Optional<CursoCargoEntity> findByCursoCargoId(Long cursoCargoId);

    Optional<CursoCargoEntity> findByCursoIdAndAnhoAndMesAndLegajoId(Long cursoId, Integer anho, Integer mes,
                                                               Long legajoId);

    @Modifying
    void deleteByCursoCargoId(Long cursoCargoId);

    @Modifying
    void deleteByCursoIdAndAnhoAndMesAndCargoTipoIdAndLegajoId(Long cursoId, Integer anho, Integer mes,
                                                               Integer cargoTipoId, Long legajoId);

}
