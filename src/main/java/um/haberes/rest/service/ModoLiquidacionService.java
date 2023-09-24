package um.haberes.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.rest.exception.ModoLiquidacionException;
import um.haberes.rest.kotlin.model.ModoLiquidacion;
import um.haberes.rest.repository.IModoLiquidacionRepository;

import java.util.List;

@Service
public class ModoLiquidacionService {

    private final IModoLiquidacionRepository repository;

    @Autowired
    public ModoLiquidacionService(IModoLiquidacionRepository repository) {
        this.repository = repository;
    }

    public List<ModoLiquidacion> findAll() {
        return repository.findAll();
    }

    public ModoLiquidacion findByModoLiquidacionId(Integer modoLiquidacionId) {
        return repository.findByModoLiquidacionId(modoLiquidacionId).orElseThrow(() -> new ModoLiquidacionException(modoLiquidacionId));
    }

}
