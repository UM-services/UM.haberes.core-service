/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.um.haberes.rest.model.LegajoContabilidad;

/**
 * @author daniel
 *
 */
public interface ILegajoContabilidadRepository extends JpaRepository<LegajoContabilidad, Long> {

	public List<LegajoContabilidad> findAllByAnhoAndMesAndDiferencia(Integer anho, Integer mes, Byte diferencia);

	public Optional<LegajoContabilidad> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

}
