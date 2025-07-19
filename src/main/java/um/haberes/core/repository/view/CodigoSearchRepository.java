/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.CodigoSearch;

/**
 * @author daniel
 *
 */
@Repository
public interface CodigoSearchRepository extends JpaRepository<CodigoSearch, Integer> {

	public List<CodigoSearch> findTop50BySearchLikeOrderByNombre(String search);

}
