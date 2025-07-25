/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.kotlin.model.CursoDesarraigo;

/**
 * @author daniel
 *
 */
public interface CursoDesarraigoRepository extends JpaRepository<CursoDesarraigo, Long> {

	public List<CursoDesarraigo> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	public List<CursoDesarraigo> findAllByLegajoIdAndAnhoAndMesAndVersion(Long legajoId, Integer anho, Integer mes,
			Integer version);

	public Optional<CursoDesarraigo> findByLegajoIdAndAnhoAndMesAndCursoId(Long legajoId, Integer anho, Integer mes,
			Long cursoId);

	public Optional<CursoDesarraigo> findByCursoDesarraigoId(Long cursoDesarraigoId);

}
