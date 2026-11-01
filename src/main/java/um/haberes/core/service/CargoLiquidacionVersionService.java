/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.model.CargoLiquidacionVersionEntity;
import um.haberes.core.repository.JpaCargoLiquidacionVersionRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoLiquidacionVersionService {

	@Autowired
	private JpaCargoLiquidacionVersionRepository repository;

	public List<CargoLiquidacionVersionEntity> saveAll(List<CargoLiquidacionVersionEntity> backups) {
		backups = repository.saveAll(backups);
		return backups;
	}

}
