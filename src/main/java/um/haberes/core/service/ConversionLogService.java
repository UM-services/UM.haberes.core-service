/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.ConversionLog;
import um.haberes.core.repository.ConversionLogRepository;

/**
 * @author daniel
 *
 */
@Service
public class ConversionLogService {
	
	@Autowired
	private ConversionLogRepository repository;

	public List<ConversionLog> findAll() {
		return repository.findAll();
	}

	public ConversionLog add(ConversionLog conversion) {
		repository.save(conversion);
		return conversion;
	}
}
