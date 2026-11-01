/**
 *
 */
package um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;

/**
 * @author daniel
 *
 */
public interface JpaPersonaRepository extends JpaRepository<PersonaEntity, Long> {

    List<PersonaEntity> findAllByLegajoIdIn(List<Long> legajos, Sort sort);

    List<PersonaEntity> findAllByApellidoLike(String apellido);

    List<PersonaEntity> findAllByLiquida(String liquida, Sort sort);

    Optional<PersonaEntity> findTopByDocumentoOrderByLegajoIdDesc(BigDecimal documento);

    Optional<PersonaEntity> findByLegajoId(Long legajoId);

}
