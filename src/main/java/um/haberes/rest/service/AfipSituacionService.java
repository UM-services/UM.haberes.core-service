package um.haberes.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.rest.exception.AfipSituacionException;
import um.haberes.rest.kotlin.model.AfipSituacion;
import um.haberes.rest.repository.IAfipSituacionRepository;

import java.util.List;

@Service
public class AfipSituacionService {

    private final IAfipSituacionRepository repository;

    @Autowired
    public AfipSituacionService(IAfipSituacionRepository repository) {
        this.repository = repository;
    }

    public List<AfipSituacion> findAll() {
        return repository.findAll();
    }

    public AfipSituacion findByAfipSituacionId(Integer afipSituacionId) {
        return repository.findByAfipSituacionId(afipSituacionId).orElseThrow(() -> new AfipSituacionException(afipSituacionId));
    }
}
