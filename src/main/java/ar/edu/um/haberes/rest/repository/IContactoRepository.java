/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import ar.edu.um.haberes.rest.kotlin.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface IContactoRepository extends JpaRepository<Contacto, Long> {

	public List<Contacto> findAllByLegajoIdIn(List<Long> legajoIds);

	public Optional<Contacto> findByLegajoId(Long legajoId);

	@Modifying
	public void deleteByLegajoId(Long legajoId);

}
