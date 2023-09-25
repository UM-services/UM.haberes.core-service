/**
 * 
 */
package um.haberes.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.rest.kotlin.model.AntiguedadLimite;

/**
 * @author daniel
 *
 */
@Repository
public interface IAntiguedadLimiteRepository extends JpaRepository<AntiguedadLimite, Integer> {

	public Optional<AntiguedadLimite> findByDesdeLessThanEqualAndHastaGreaterThanEqual(Integer meses_docentes, Integer meses_docentes2);

}
