/**
 *
 */
package um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.rest.kotlin.model.Curso;

/**
 * @author daniel
 *
 */
@Repository
public interface ICursoRepository extends JpaRepository<Curso, Long>, ICursoRepositoryCustom {

    List<Curso> findAllByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId, Sort ascending);

    List<Curso> findAllByCursoIdIn(List<Long> cursoIds);

    List<Curso> findAllByFacultadId(Integer facultadId);

    List<Curso> findAllByFacultadIdAndGeograficaIdAndCursoIdInOrderByNombre(Integer facultadId, Integer geograficaId, List<Long> cursoIds);

    Optional<Curso> findByCursoId(Long cursoId);

    void deleteByCursoId(Long cursoId);

}
