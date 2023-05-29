/**
 * 
 */
package um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.view.CategoriaByPeriodo;
import um.haberes.rest.model.view.pk.CategoriaByPeriodoPK;

/**
 * @author daniel
 *
 */
@Repository
public interface ICategoriaByPeriodoRepository extends JpaRepository<CategoriaByPeriodo, CategoriaByPeriodoPK> {

	public List<CategoriaByPeriodo> findAllByAnhoAndMesAndCategoriaIdIn(Integer anho, Integer mes,
			List<Integer> categorias);

}
