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
import um.haberes.core.model.LegajoBancoEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaLegajoBancoRepository extends JpaRepository<LegajoBancoEntity, Long> {

	List<LegajoBancoEntity> findAllByLegajoId(Long legajoId, Sort sort);

	List<LegajoBancoEntity> findAllByLegajoIdAndAnhoAndMesAndCbuLike(Long legajoId, Integer anho, Integer mes,
			String cbu);

	List<LegajoBancoEntity> findAllByLegajoIdAndAnhoAndMesAndCbuNotLike(Long legajoId, Integer anho, Integer mes,
			String cbu);

	List<LegajoBancoEntity> findAllByAnhoAndMes(Integer anho, Integer mes);

	List<LegajoBancoEntity> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	List<LegajoBancoEntity> findAllByLegajoIdInAndAnhoAndMesAndCbuLike(List<Long> legajoIds, Integer anho, Integer mes,
			String cbuLike);

	List<LegajoBancoEntity> findAllByLegajoIdInAndAnhoAndMesAndCbuNotLike(List<Long> legajoIds, Integer anho,
			Integer mes, String cbuNotLike);

	Optional<LegajoBancoEntity> findByLegajoIdAndAnhoAndMesAndResto(Long legajoId, Integer anho, Integer mes, Byte resto);

	Optional<LegajoBancoEntity> findByLegajoBancoId(Long legajoBancoId);

	Optional<LegajoBancoEntity> findTopByLegajoIdOrderByAnhoDescMesDesc(Long legajoId);

	Optional<LegajoBancoEntity> findByLegajoIdAndAnhoAndMesAndCbu(Long legajoId, Integer anho, Integer mes,
			String cbu);

	@Modifying
	void deleteAllByAnhoAndMes(Integer anho, Integer mes);

	@Modifying
	void deleteByLegajoBancoId(Long legajoBancoId);

}
