/**
 * 
 */
package um.haberes.rest.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.rest.kotlin.model.Persona;

/**
 * @author daniel
 *
 */
public interface IPersonaRepository extends JpaRepository<Persona, Long> {
	
	public List<Persona> findAllByLegajoIdIn(List<Long> legajos, Sort sort);

	public List<Persona> findAllByApellidoLike(String apellido);

	public List<Persona> findAllByLiquida(String liquida, Sort sort);

	public Optional<Persona> findByDocumento(BigDecimal documento);
	
	public Optional<Persona> findByLegajoId(Long legajoId);

}
