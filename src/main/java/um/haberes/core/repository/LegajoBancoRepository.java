/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.LegajoBanco;

/**
 * @author daniel
 *
 */
@Repository
public interface LegajoBancoRepository extends JpaRepository<LegajoBanco, Long> {

	List<LegajoBanco> findAllByLegajoId(Long legajoId, Sort sort);

	List<LegajoBanco> findAllByLegajoIdAndAnhoAndMesAndCbuLike(Long legajoId, Integer anho, Integer mes,
			String cbu);

	List<LegajoBanco> findAllByLegajoIdAndAnhoAndMesAndCbuNotLike(Long legajoId, Integer anho, Integer mes,
			String cbu);

	List<LegajoBanco> findAllByAnhoAndMes(Integer anho, Integer mes);

	List<LegajoBanco> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	List<LegajoBanco> findAllByLegajoIdInAndAnhoAndMesAndCbuLike(List<Long> legajoIds, Integer anho, Integer mes,
			String cbuLike);

	List<LegajoBanco> findAllByLegajoIdInAndAnhoAndMesAndCbuNotLike(List<Long> legajoIds, Integer anho,
			Integer mes, String cbuNotLike);

	Optional<LegajoBanco> findByLegajoIdAndAnhoAndMesAndResto(Long legajoId, Integer anho, Integer mes, Byte resto);

	Optional<LegajoBanco> findByLegajoBancoId(Long legajoBancoId);

	Optional<LegajoBanco> findTopByLegajoIdOrderByAnhoDescMesDesc(Long legajoId);

	Optional<LegajoBanco> findByLegajoIdAndAnhoAndMesAndCbu(Long legajoId, Integer anho, Integer mes,
			String cbu);

	@Modifying
	void deleteAllByAnhoAndMes(Integer anho, Integer mes);

	@Modifying
	void deleteByLegajoBancoId(Long legajoBancoId);

}
