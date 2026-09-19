/**
 * 
 */
package um.haberes.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.AntiguedadLimiteEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaAntiguedadLimiteRepository extends JpaRepository<AntiguedadLimiteEntity, Integer> {

	public Optional<AntiguedadLimiteEntity> findByDesdeLessThanEqualAndHastaGreaterThanEqual(Integer meses_docentes, Integer meses_docentes2);

}
