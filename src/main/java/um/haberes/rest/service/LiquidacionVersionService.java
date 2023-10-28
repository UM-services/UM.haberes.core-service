/**
 *
 */
package um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.kotlin.model.LiquidacionVersion;
import um.haberes.rest.repository.ILiquidacionVersionRepository;

/**
 * @author daniel
 */
@Service
public class LiquidacionVersionService {

    @Autowired
    private ILiquidacionVersionRepository repository;

    public LiquidacionVersion add(LiquidacionVersion liquidacionVersion) {
        return repository.save(liquidacionVersion);
    }

    public List<LiquidacionVersion> saveAll(List<LiquidacionVersion> backups) {
        return repository.saveAll(backups);
    }

}
