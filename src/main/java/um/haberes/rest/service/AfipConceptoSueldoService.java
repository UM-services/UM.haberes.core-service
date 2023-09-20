package um.haberes.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.rest.exception.AfipConceptoSueldoException;
import um.haberes.rest.kotlin.model.AfipConceptoSueldo;
import um.haberes.rest.kotlin.view.AfipConceptoSueldoSearch;
import um.haberes.rest.repository.IAfipConceptoSueldoRepository;
import um.haberes.rest.repository.view.IAfipConceptoSueldoSearchRepository;

import java.util.List;

@Service
public class AfipConceptoSueldoService {

    @Autowired
    private IAfipConceptoSueldoRepository repository;

    @Autowired
    private IAfipConceptoSueldoSearchRepository afipConceptoSueldoSearchRepository;

    public AfipConceptoSueldo findByAfipConceptoSueldoId(Long afipConceptoSueldoId) {
        return repository.findByAfipConceptoSueldoId(afipConceptoSueldoId).orElseThrow(() -> new AfipConceptoSueldoException(afipConceptoSueldoId));
    }

    public List<AfipConceptoSueldoSearch> findAllByAsignadoAndConditions(List<String> conditions) {
        return afipConceptoSueldoSearchRepository.findAllByAsignadoAndConditions(conditions);
    }
}
