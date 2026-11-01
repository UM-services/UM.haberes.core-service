/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.model.ConversionLogEntity;
import um.haberes.core.repository.JpaConversionLogRepository;

/**
 * @author daniel
 *
 */
@Service
public class ConversionLogService {
	
	@Autowired
	private JpaConversionLogRepository repository;

	public List<ConversionLogEntity> findAll() {
		return repository.findAll();
	}

	public ConversionLogEntity add(ConversionLogEntity conversion) {
		repository.save(conversion);
		return conversion;
	}
}
