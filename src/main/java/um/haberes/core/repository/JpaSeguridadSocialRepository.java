/**
 * 
 */
package um.haberes.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.SeguridadSocialEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaSeguridadSocialRepository extends JpaRepository<SeguridadSocialEntity, Long> {

	public Optional<SeguridadSocialEntity> findByAnhoAndMes(Integer anho, Integer mes);

	public Optional<SeguridadSocialEntity> findBySeguridadSocialId(Long seguridadSocialId);

}
