/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.LegajoBanco;

/**
 * @author daniel
 *
 */
@Repository
public interface ILegajoBancoRepository extends JpaRepository<LegajoBanco, Long> {

	public List<LegajoBanco> findAllByLegajoId(Long legajoId, Sort sort);

	public List<LegajoBanco> findAllByLegajoIdAndAnhoAndMesAndCbuLike(Long legajoId, Integer anho, Integer mes,
			String cbu);

	public List<LegajoBanco> findAllByLegajoIdAndAnhoAndMesAndCbuNotLike(Long legajoId, Integer anho, Integer mes,
			String cbu);

	public List<LegajoBanco> findAllByAnhoAndMes(Integer anho, Integer mes);

	public List<LegajoBanco> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	public List<LegajoBanco> findAllByLegajoIdInAndAnhoAndMesAndCbuLike(List<Long> legajoIds, Integer anho, Integer mes,
			String cbuLike);

	public List<LegajoBanco> findAllByLegajoIdInAndAnhoAndMesAndCbuNotLike(List<Long> legajoIds, Integer anho,
			Integer mes, String cbuNotLike);

	public Optional<LegajoBanco> findByLegajoIdAndAnhoAndMesAndResto(Long legajoId, Integer anho, Integer mes, Byte resto);

	public Optional<LegajoBanco> findByLegajoBancoId(Long legajoBancoId);

	public Optional<LegajoBanco> findTopByLegajoIdOrderByAnhoDescMesDesc(Long legajoId);

	public Optional<LegajoBanco> findByLegajoIdAndAnhoAndMesAndCbu(Long legajoId, Integer anho, Integer mes,
			String cbu);

	@Modifying
	public void deleteAllByAnhoAndMes(Integer anho, Integer mes);

	@Modifying
	public void deleteByLegajoBancoId(Long legajoBancoId);

}
