/**
 * 
 */
package um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.Letra;

/**
 * @author daniel
 *
 */
@Repository
public interface ILetraRepository extends JpaRepository<Letra, Long> {

	public List<Letra> findAllByAnhoAndMes(Integer anho, Integer mes, Pageable pageable);

	public Optional<Letra> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	public Optional<Letra> findByLetraId(Long letraId);

	@Modifying
	public void deleteAllByAnhoAndMes(Integer anho, Integer mes);

	@Modifying
	public void deleteByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
