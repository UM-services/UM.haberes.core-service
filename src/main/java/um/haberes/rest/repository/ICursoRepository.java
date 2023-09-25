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

	public List<Curso> findAllByFacultadIdAndGeograficaId(Integer facultadId, Integer geograficaId, Sort ascending);

	public List<Curso> findAllByCursoIdIn(List<Long> cursoIds);

	public Optional<Curso> findByCursoId(Long cursoId);

	public void deleteByCursoId(Long cursoId);

}
