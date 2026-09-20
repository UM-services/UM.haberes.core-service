/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import um.haberes.core.model.ContactoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaContactoRepository extends JpaRepository<ContactoEntity, Long> {

	public List<ContactoEntity> findAllByLegajoIdIn(List<Long> legajoIds);

	public Optional<ContactoEntity> findByLegajoId(Long legajoId);

	@Modifying
	public void deleteByLegajoId(Long legajoId);

}
