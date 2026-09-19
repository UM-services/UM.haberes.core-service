/**
 * 
 */
package um.haberes.core.repository;

import java.util.Optional;

import um.haberes.core.model.BuildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaBuildRepository extends JpaRepository<BuildEntity, Long> {

	public Optional<BuildEntity> findTopByOrderByBuildDesc();

}
