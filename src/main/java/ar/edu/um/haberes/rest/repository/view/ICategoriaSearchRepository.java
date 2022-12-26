/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.view.CategoriaSearch;

/**
 * @author daniel
 *
 */
@Repository
public interface ICategoriaSearchRepository extends JpaRepository<CategoriaSearch, Integer> {

	public List<CategoriaSearch> findTop50BySearchLike(String string, Sort ascending);

}
