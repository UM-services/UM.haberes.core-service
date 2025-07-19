/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.AdicionalCursoRango;

/**
 * @author daniel
 *
 */
@Repository
public interface AdicionalCursoRangoRepository extends JpaRepository<AdicionalCursoRango, Long> {

	public List<AdicionalCursoRango> findAllByAdicionalCursoTablaIdOrderByHorasDesde(Long adicionalCursoTablaId);

	public Optional<AdicionalCursoRango> findByAdicionalCursoRangoId(Long adicionalCursoRangoId);

	@Modifying
	public void deleteByAdicionalCursoRangoId(Long adicionalCursoRangoId);

}
