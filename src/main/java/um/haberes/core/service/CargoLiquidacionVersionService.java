/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.CargoLiquidacionVersion;
import um.haberes.core.repository.CargoLiquidacionVersionRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoLiquidacionVersionService {

	@Autowired
	private CargoLiquidacionVersionRepository repository;

	public List<CargoLiquidacionVersion> saveAll(List<CargoLiquidacionVersion> backups) {
		backups = repository.saveAll(backups);
		return backups;
	}

}
