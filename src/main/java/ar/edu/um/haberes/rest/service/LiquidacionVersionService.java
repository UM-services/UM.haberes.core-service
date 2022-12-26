/**
 * 
 */
package ar.edu.um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.LiquidacionVersion;
import ar.edu.um.haberes.rest.repository.ILiquidacionVersionRepository;

/**
 * @author daniel
 *
 */
@Service
public class LiquidacionVersionService {

	@Autowired
	private ILiquidacionVersionRepository repository;

	public LiquidacionVersion add(LiquidacionVersion liquidacionVersion) {
		repository.save(liquidacionVersion);
		return liquidacionVersion;
	}

	public List<LiquidacionVersion> saveAll(List<LiquidacionVersion> backups) {
		repository.saveAll(backups);
		return backups;
	}

}
