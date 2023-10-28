/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.kotlin.model.CargoLiquidacionVersion;
import um.haberes.rest.repository.ICargoLiquidacionVersionRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoLiquidacionVersionService {

	@Autowired
	private ICargoLiquidacionVersionRepository repository;

	public List<CargoLiquidacionVersion> saveAll(List<CargoLiquidacionVersion> backups) {
		backups = repository.saveAll(backups);
		return backups;
	}

}
