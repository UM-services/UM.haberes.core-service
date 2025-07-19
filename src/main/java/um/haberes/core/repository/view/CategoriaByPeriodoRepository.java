/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.CategoriaByPeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface CategoriaByPeriodoRepository extends JpaRepository<CategoriaByPeriodo, String> {

	public List<CategoriaByPeriodo> findAllByAnhoAndMesAndCategoriaIdIn(Integer anho, Integer mes,
			List<Integer> categorias);

}
