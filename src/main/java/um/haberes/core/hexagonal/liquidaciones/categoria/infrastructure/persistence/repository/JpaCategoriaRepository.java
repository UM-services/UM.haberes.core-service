/**
 * 
 */
package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.entity.CategoriaEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {

	List<CategoriaEntity> findAllByDocente(Byte docente, Sort sort);

	List<CategoriaEntity> findAllByNoDocente(Byte noDocente, Sort sort);

	List<CategoriaEntity> findAllByCategoriaIdIn(List<Integer> categoriaIds);

	List<CategoriaEntity> findAllByCategoriaIdNotIn(List<Integer> categoriaIds);

	Optional<CategoriaEntity> findByCategoriaId(Integer categoriaId);

	Optional<CategoriaEntity> findTopByOrderByCategoriaIdDesc();

	List<CategoriaEntity> findAllByCategoriaIdIn(Set<Integer> categoriaIds);

}
