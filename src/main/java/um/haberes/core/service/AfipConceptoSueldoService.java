package um.haberes.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.exception.AfipConceptoSueldoException;
import um.haberes.core.kotlin.model.AfipConceptoSueldo;
import um.haberes.core.kotlin.model.view.AfipConceptoSueldoSearch;
import um.haberes.core.repository.IAfipConceptoSueldoRepository;
import um.haberes.core.repository.view.IAfipConceptoSueldoSearchRepository;

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
