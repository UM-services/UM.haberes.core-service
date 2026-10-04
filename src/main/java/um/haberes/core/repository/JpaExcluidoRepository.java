/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.ExcluidoEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaExcluidoRepository extends JpaRepository<ExcluidoEntity, Long> {

	public List<ExcluidoEntity> findAllByAnhoAndMes(Integer anho, Integer mes);

	public Optional<ExcluidoEntity> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	public Optional<ExcluidoEntity> findByExcluidoId(Long excluidoId);

	@Modifying
	public void deleteByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
