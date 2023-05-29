/**
 * 
 */
package um.haberes.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.CursoFusion;

/**
 * @author daniel
 *
 */
@Repository
public interface ICursoFusionRepository extends JpaRepository<CursoFusion, Long> {

	public List<CursoFusion> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	public List<CursoFusion> findAllByLegajoIdAndAnhoAndMesAndFacultadId(Long legajoId, Integer anho, Integer mes,
			Integer facultadId);

	public List<CursoFusion> findAllByAnhoAndMes(Integer anho, Integer mes);

	@Modifying
	public void deleteAllByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(Long legajoId, Integer anho, Integer mes,
			Integer facultadId, Integer geograficaId);

	public void deleteByCursoFusionId(Long cursoFusionId);

	@Modifying
	public void deleteAllByLegajoIdInAndAnhoAndMes(List<Long> ids, Integer anho, Integer mes);

	@Modifying
	public void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
