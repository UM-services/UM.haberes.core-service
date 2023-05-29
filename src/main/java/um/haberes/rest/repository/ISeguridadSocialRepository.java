/**
 * 
 */
package um.haberes.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.SeguridadSocial;

/**
 * @author daniel
 *
 */
@Repository
public interface ISeguridadSocialRepository extends JpaRepository<SeguridadSocial, Long> {

	public Optional<SeguridadSocial> findByAnhoAndMes(Integer anho, Integer mes);

	public Optional<SeguridadSocial> findBySeguridadSocialId(Long seguridadSocialId);

}
