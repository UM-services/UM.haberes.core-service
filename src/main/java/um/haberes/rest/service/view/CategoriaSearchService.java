/**
 * 
 */
package um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.kotlin.view.CategoriaSearch;
import um.haberes.rest.repository.view.ICategoriaSearchRepository;

/**
 * @author daniel
 *
 */
@Service
public class CategoriaSearchService {
	
	@Autowired
	private ICategoriaSearchRepository repository;

	public List<CategoriaSearch> findTop50BySearchLike(String search, Sort sort) {
		return repository.findTop50BySearchLike(search, sort);
	}

}
