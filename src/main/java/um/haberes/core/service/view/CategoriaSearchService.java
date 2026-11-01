/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.core.model.view.CategoriaSearch;
import um.haberes.core.repository.view.JpaCategoriaSearchRepository;

/**
 * @author daniel
 *
 */
@Service
public class CategoriaSearchService {
	
	@Autowired
	private JpaCategoriaSearchRepository repository;

	public List<CategoriaSearch> findTop50BySearchLike(String search, Sort sort) {
		return repository.findTop50BySearchLike(search, sort);
	}

}
