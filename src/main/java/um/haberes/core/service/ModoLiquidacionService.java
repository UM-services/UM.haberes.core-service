package um.haberes.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.exception.ModoLiquidacionException;
import um.haberes.core.model.ModoLiquidacionEntity;
import um.haberes.core.repository.JpaModoLiquidacionRepository;

import java.util.List;

@Service
public class ModoLiquidacionService {

    private final JpaModoLiquidacionRepository repository;

    @Autowired
    public ModoLiquidacionService(JpaModoLiquidacionRepository repository) {
        this.repository = repository;
    }

    public List<ModoLiquidacionEntity> findAll() {
        return repository.findAll();
    }

    public ModoLiquidacionEntity findByModoLiquidacionId(Integer modoLiquidacionId) {
        return repository.findByModoLiquidacionId(modoLiquidacionId).orElseThrow(() -> new ModoLiquidacionException(modoLiquidacionId));
    }

}
