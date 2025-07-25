/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.Excluido;

/**
 * @author daniel
 *
 */
@Repository
public interface ExcluidoRepository extends JpaRepository<Excluido, Long> {

	public List<Excluido> findAllByAnhoAndMes(Integer anho, Integer mes);

	public Optional<Excluido> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	public Optional<Excluido> findByExcluidoId(Long excluidoId);

	@Modifying
	public void deleteByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
