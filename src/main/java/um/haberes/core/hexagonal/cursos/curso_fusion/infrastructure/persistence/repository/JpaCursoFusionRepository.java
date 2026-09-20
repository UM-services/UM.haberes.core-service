/**
 * 
 */
package um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.persistence.entity.CursoFusionEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCursoFusionRepository extends JpaRepository<CursoFusionEntity, Long> {

	List<CursoFusionEntity> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	List<CursoFusionEntity> findAllByLegajoIdAndAnhoAndMesAndFacultadId(Long legajoId, Integer anho, Integer mes,
			Integer facultadId);

	List<CursoFusionEntity> findAllByAnhoAndMes(Integer anho, Integer mes);

	@Modifying
	void deleteAllByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(Long legajoId, Integer anho, Integer mes,
			Integer facultadId, Integer geograficaId);

	void deleteByCursoFusionId(Long cursoFusionId);

	@Modifying
	void deleteAllByLegajoIdInAndAnhoAndMes(List<Long> ids, Integer anho, Integer mes);

	@Modifying
	void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
