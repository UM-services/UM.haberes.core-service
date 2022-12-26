/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.view.AntiguedadPeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface IAntiguedadPeriodoRepository extends JpaRepository<AntiguedadPeriodo, Long> {

	public Optional<AntiguedadPeriodo> findTopByLegajoIdAndPeriodoLessThanOrderByPeriodoDesc(Long legajoId,
			Long periodo);

	public Optional<AntiguedadPeriodo> findTopByLegajoIdAndPeriodoLessThanEqualOrderByPeriodoDesc(Long legajoId,
			Long periodo);
	
}
