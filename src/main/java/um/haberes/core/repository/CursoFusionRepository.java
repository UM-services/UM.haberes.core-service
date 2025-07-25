/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.CursoFusion;

/**
 * @author daniel
 *
 */
@Repository
public interface CursoFusionRepository extends JpaRepository<CursoFusion, Long> {

	List<CursoFusion> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	List<CursoFusion> findAllByLegajoIdAndAnhoAndMesAndFacultadId(Long legajoId, Integer anho, Integer mes,
			Integer facultadId);

	List<CursoFusion> findAllByAnhoAndMes(Integer anho, Integer mes);

	@Modifying
	void deleteAllByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(Long legajoId, Integer anho, Integer mes,
			Integer facultadId, Integer geograficaId);

	void deleteByCursoFusionId(Long cursoFusionId);

	@Modifying
	void deleteAllByLegajoIdInAndAnhoAndMes(List<Long> ids, Integer anho, Integer mes);

	@Modifying
	void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
