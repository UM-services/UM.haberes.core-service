/**
 * 
 */
package um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.rest.kotlin.view.CodigoSearch;

/**
 * @author daniel
 *
 */
@Repository
public interface ICodigoSearchRepository extends JpaRepository<CodigoSearch, Integer> {

	public List<CodigoSearch> findTop50BySearchLikeOrderByNombre(String search);

}
