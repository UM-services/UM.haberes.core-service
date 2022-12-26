/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.CargoLiquidacionVersion;
import ar.edu.um.haberes.rest.repository.ICargoVersionRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoVersionService {

	@Autowired
	private ICargoVersionRepository repository;

	public List<CargoLiquidacionVersion> saveAll(List<CargoLiquidacionVersion> backups) {
		repository.saveAll(backups);
		return backups;
	}

}
