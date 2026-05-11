/**
 * 
 */
package um.haberes.core.hexagonal.geografica.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.entity.GeograficaEntity;

/**
 * @author dquinteros
 *
 */
@Repository
public interface JpaGeograficaRepository extends JpaRepository<GeograficaEntity, Integer> {

	List<GeograficaEntity> findAllByGeograficaIdIn(List<Integer> ids);

	Optional<GeograficaEntity> findByGeograficaId(Integer geograficaId);

}
