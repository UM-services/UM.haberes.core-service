/**
 * 
 */
package um.haberes.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.Codigo;

/**
 * @author daniel
 *
 */
@Repository
public interface ICodigoRepository extends JpaRepository<Codigo, Integer> {

	public List<Codigo> findAllByTransferible(Byte transferible);

	public List<Codigo> findAllByCodigoIdIn(List<Integer> codigoIds, Sort sort);

	public Optional<Codigo> findTopByOrderByCodigoId();

}
