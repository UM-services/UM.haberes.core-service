/**
 * 
 */
package ar.edu.um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.um.haberes.rest.model.Antiguedad;

/**
 * @author daniel
 *
 */
public interface IAntiguedadRepository extends JpaRepository<Antiguedad, Long> {

	public List<Antiguedad> findAllByAnhoAndMes(Integer anho, Integer mes, Pageable pageable);

	public Optional<Antiguedad> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

	public Optional<Antiguedad> findByAntiguedadId(Long antiguedadId);

}
