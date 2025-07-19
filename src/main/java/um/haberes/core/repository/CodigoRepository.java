/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.Codigo;

/**
 * @author daniel
 *
 */
@Repository
public interface CodigoRepository extends JpaRepository<Codigo, Integer> {

	public List<Codigo> findAllByTransferible(Byte transferible);

	public List<Codigo> findAllByCodigoIdIn(List<Integer> codigoIds, Sort sort);

	public Optional<Codigo> findTopByOrderByCodigoId();

}
