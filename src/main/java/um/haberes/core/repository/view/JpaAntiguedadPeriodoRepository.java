/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.view.AntiguedadPeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaAntiguedadPeriodoRepository extends JpaRepository<AntiguedadPeriodo, Long> {

	public Optional<AntiguedadPeriodo> findTopByLegajoIdAndPeriodoLessThanOrderByPeriodoDesc(Long legajoId,
			Long periodo);

	public Optional<AntiguedadPeriodo> findTopByLegajoIdAndPeriodoLessThanEqualOrderByPeriodoDesc(Long legajoId,
			Long periodo);
	
}
