/**
 *
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.model.LiquidacionVersionEntity;
import um.haberes.core.repository.JpaLiquidacionVersionRepository;

/**
 * @author daniel
 */
@Service
public class LiquidacionVersionService {

    @Autowired
    private JpaLiquidacionVersionRepository repository;

    public LiquidacionVersionEntity add(LiquidacionVersionEntity liquidacionVersion) {
        return repository.save(liquidacionVersion);
    }

    public List<LiquidacionVersionEntity> saveAll(List<LiquidacionVersionEntity> backups) {
        return repository.saveAll(backups);
    }

}
