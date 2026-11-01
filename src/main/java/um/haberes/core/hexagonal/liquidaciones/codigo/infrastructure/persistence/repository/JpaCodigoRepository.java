/**
 * 
 */
package um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.entity.CodigoEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCodigoRepository extends JpaRepository<CodigoEntity, Integer> {

	public List<CodigoEntity> findAllByTransferible(Byte transferible);

	public List<CodigoEntity> findAllByCodigoIdIn(List<Integer> codigoIds, Sort sort);

	public Optional<CodigoEntity> findTopByOrderByCodigoId();

}
