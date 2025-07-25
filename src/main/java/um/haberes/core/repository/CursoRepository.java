/**
 *
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.Curso;

/**
 * @author daniel
 *
 */
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>, CursoRepositoryCustom {

    List<Curso> findAllByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId, Sort ascending);

    List<Curso> findAllByCursoIdIn(List<Long> cursoIds);

    List<Curso> findAllByFacultadId(Integer facultadId);

    List<Curso> findAllByFacultadIdAndGeograficaIdAndCursoIdInOrderByNombre(Integer facultadId, Integer geograficaId, List<Long> cursoIds);

    Optional<Curso> findByCursoId(Long cursoId);

    void deleteByCursoId(Long cursoId);

}
