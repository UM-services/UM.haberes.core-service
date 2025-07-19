/**
 * 
 */
package um.haberes.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import java.util.Set;
import um.haberes.core.kotlin.model.CategoriaPeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface CategoriaPeriodoRepository extends JpaRepository<CategoriaPeriodo, Long> {

	public List<CategoriaPeriodo> findAllByAnhoAndMes(Integer anho, Integer mes);

	public Optional<CategoriaPeriodo> findByCategoriaPeriodoId(Long categoriaPeriodoId);

	public Optional<CategoriaPeriodo> findByCategoriaIdAndAnhoAndMes(Integer categoriaId, Integer anho, Integer mes);

	@Modifying
	public void deleteAllByAnhoAndMes(Integer anho, Integer mes);

	public List<CategoriaPeriodo> findAllByCategoriaIdInAndAnhoAndMes(Set<Integer> categoriaIds, Integer anho, Integer mes);

}
