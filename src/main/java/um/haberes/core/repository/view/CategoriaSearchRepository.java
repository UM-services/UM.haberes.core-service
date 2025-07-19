/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.CategoriaSearch;

/**
 * @author daniel
 *
 */
@Repository
public interface CategoriaSearchRepository extends JpaRepository<CategoriaSearch, Integer> {

	public List<CategoriaSearch> findTop50BySearchLike(String string, Sort ascending);

}
