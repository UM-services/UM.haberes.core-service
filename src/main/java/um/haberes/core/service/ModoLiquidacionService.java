package um.haberes.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.exception.ModoLiquidacionException;
import um.haberes.core.kotlin.model.ModoLiquidacion;
import um.haberes.core.repository.ModoLiquidacionRepository;

import java.util.List;

@Service
public class ModoLiquidacionService {

    private final ModoLiquidacionRepository repository;

    @Autowired
    public ModoLiquidacionService(ModoLiquidacionRepository repository) {
        this.repository = repository;
    }

    public List<ModoLiquidacion> findAll() {
        return repository.findAll();
    }

    public ModoLiquidacion findByModoLiquidacionId(Integer modoLiquidacionId) {
        return repository.findByModoLiquidacionId(modoLiquidacionId).orElseThrow(() -> new ModoLiquidacionException(modoLiquidacionId));
    }

}
