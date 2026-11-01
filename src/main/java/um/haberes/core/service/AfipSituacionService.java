package um.haberes.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.exception.AfipSituacionException;
import um.haberes.core.model.AfipSituacionEntity;
import um.haberes.core.repository.JpaAfipSituacionRepository;

import java.util.List;

@Service
public class AfipSituacionService {

    private final JpaAfipSituacionRepository repository;

    @Autowired
    public AfipSituacionService(JpaAfipSituacionRepository repository) {
        this.repository = repository;
    }

    public List<AfipSituacionEntity> findAll() {
        return repository.findAll();
    }

    public AfipSituacionEntity findByAfipSituacionId(Integer afipSituacionId) {
        return repository.findByAfipSituacionId(afipSituacionId).orElseThrow(() -> new AfipSituacionException(afipSituacionId));
    }
}
