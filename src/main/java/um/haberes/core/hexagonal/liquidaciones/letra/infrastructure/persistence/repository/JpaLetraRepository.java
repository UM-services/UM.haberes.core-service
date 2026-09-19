/**
 * 
 */
package um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.persistence.entity.LetraEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaLetraRepository extends JpaRepository<LetraEntity, Long> {

	public List<LetraEntity> findAllByAnhoAndMes(Integer anho, Integer mes, Pageable pageable);

	public Optional<LetraEntity> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	public Optional<LetraEntity> findByLetraId(Long letraId);

	@Modifying
	public void deleteAllByAnhoAndMes(Integer anho, Integer mes);

	@Modifying
	public void deleteByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
