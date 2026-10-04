/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.model.NivelEntity;
import um.haberes.core.repository.JpaNivelRepository;

/**
 * @author daniel
 *
 */
@Service
public class NivelService {
	
	@Autowired
	private JpaNivelRepository repository;

	public List<NivelEntity> findAll() {
		return repository.findAll();
	}
}
