/**
 *
 */
package um.haberes.core.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.kotlin.model.Persona;

/**
 * @author daniel
 *
 */
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    List<Persona> findAllByLegajoIdIn(List<Long> legajos, Sort sort);

    List<Persona> findAllByApellidoLike(String apellido);

    List<Persona> findAllByLiquida(String liquida, Sort sort);

    Optional<Persona> findTopByDocumentoOrderByLegajoIdDesc(BigDecimal documento);

    Optional<Persona> findByLegajoId(Long legajoId);

}
