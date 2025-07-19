/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;
import um.haberes.core.kotlin.model.Categoria;

/**
 * @author daniel
 *
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

	List<Categoria> findAllByDocente(Byte docente, Sort sort);

	List<Categoria> findAllByNoDocente(Byte noDocente, Sort sort);

	List<Categoria> findAllByCategoriaIdIn(List<Integer> categoriaIds);

	List<Categoria> findAllByCategoriaIdNotIn(List<Integer> categoriaIds);

	Optional<Categoria> findByCategoriaId(Integer categoriaId);

	Optional<Categoria> findTopByOrderByCategoriaIdDesc();

	List<Categoria> findAllByCategoriaIdIn(Set<Integer> categoriaIds);

}
