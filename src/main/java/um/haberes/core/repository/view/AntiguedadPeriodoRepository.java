/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.AntiguedadPeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface AntiguedadPeriodoRepository extends JpaRepository<AntiguedadPeriodo, Long> {

	public Optional<AntiguedadPeriodo> findTopByLegajoIdAndPeriodoLessThanOrderByPeriodoDesc(Long legajoId,
			Long periodo);

	public Optional<AntiguedadPeriodo> findTopByLegajoIdAndPeriodoLessThanEqualOrderByPeriodoDesc(Long legajoId,
			Long periodo);
	
}
