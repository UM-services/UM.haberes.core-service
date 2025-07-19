/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.kotlin.model.LegajoContabilidad;

/**
 * @author daniel
 *
 */
public interface LegajoContabilidadRepository extends JpaRepository<LegajoContabilidad, Long> {

	public List<LegajoContabilidad> findAllByAnhoAndMesAndDiferencia(Integer anho, Integer mes, Byte diferencia);

	public Optional<LegajoContabilidad> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
