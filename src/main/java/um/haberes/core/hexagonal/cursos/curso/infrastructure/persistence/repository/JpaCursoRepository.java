/**
 *
 */
package um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.entity.CursoEntity;
import um.haberes.core.repository.JpaCursoRepositoryCustom;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCursoRepository extends JpaRepository<CursoEntity, Long>, JpaCursoRepositoryCustom {

    List<CursoEntity> findAllByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId, Sort ascending);

    List<CursoEntity> findAllByCursoIdIn(List<Long> cursoIds);

    List<CursoEntity> findAllByFacultadId(Integer facultadId);

    List<CursoEntity> findAllByFacultadIdAndGeograficaIdAndCursoIdInOrderByNombre(Integer facultadId, Integer geograficaId, List<Long> cursoIds);

    Optional<CursoEntity> findByCursoId(Long cursoId);

    void deleteByCursoId(Long cursoId);

}
