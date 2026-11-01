/**
 *
 */
package um.haberes.core.hexagonal.cursos.curso_desarraigo.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.cursos.curso_desarraigo.infrastructure.persistence.entity.CursoDesarraigoEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCursoDesarraigoRepository extends JpaRepository<CursoDesarraigoEntity, Long> {

	List<CursoDesarraigoEntity> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	List<CursoDesarraigoEntity> findAllByLegajoIdAndAnhoAndMesAndVersion(Long legajoId, Integer anho, Integer mes,
			Integer version);

	Optional<CursoDesarraigoEntity> findByLegajoIdAndAnhoAndMesAndCursoId(Long legajoId, Integer anho, Integer mes,
			Long cursoId);

	Optional<CursoDesarraigoEntity> findByCursoDesarraigoId(Long cursoDesarraigoId);

}
