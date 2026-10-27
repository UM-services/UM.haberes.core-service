/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.AdicionalCursoRangoEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaAdicionalCursoRangoRepository extends JpaRepository<AdicionalCursoRangoEntity, Long> {

	public List<AdicionalCursoRangoEntity> findAllByAdicionalCursoTablaIdOrderByHorasDesde(Long adicionalCursoTablaId);

	public Optional<AdicionalCursoRangoEntity> findByAdicionalCursoRangoId(Long adicionalCursoRangoId);

	public void deleteByAdicionalCursoRangoId(Long adicionalCursoRangoId);

}
