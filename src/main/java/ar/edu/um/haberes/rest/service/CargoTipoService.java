/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.exception.CargoTipoNotFoundException;
import ar.edu.um.haberes.rest.model.CargoTipo;
import ar.edu.um.haberes.rest.repository.ICargoTipoRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoTipoService {

	@Autowired
	private ICargoTipoRepository repository;

	public List<CargoTipo> findAll() {
		return repository.findAll();
	}

	public List<CargoTipo> findAllByCargoTipoIdIn(List<Integer> cargoTipoIds) {
		return repository.findAllByCargoTipoIdIn(cargoTipoIds);
	}

	public CargoTipo findByCargoTipoId(Integer cargoTipoId) {
		return repository.findByCargoTipoId(cargoTipoId).orElseThrow(() -> new CargoTipoNotFoundException(cargoTipoId));
	}

}
