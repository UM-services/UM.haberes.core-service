package um.haberes.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.exception.AfipConceptoSueldoException;
import um.haberes.core.kotlin.model.AfipConceptoSueldo;
import um.haberes.core.kotlin.model.view.AfipConceptoSueldoSearch;
import um.haberes.core.repository.AfipConceptoSueldoRepository;
import um.haberes.core.repository.view.AfipConceptoSueldoSearchRepository;

import java.util.List;

@Service
public class AfipConceptoSueldoService {

    @Autowired
    private AfipConceptoSueldoRepository repository;

    @Autowired
    private AfipConceptoSueldoSearchRepository afipConceptoSueldoSearchRepository;

    public AfipConceptoSueldo findByAfipConceptoSueldoId(Long afipConceptoSueldoId) {
        return repository.findByAfipConceptoSueldoId(afipConceptoSueldoId).orElseThrow(() -> new AfipConceptoSueldoException(afipConceptoSueldoId));
    }

    public List<AfipConceptoSueldoSearch> findAllByAsignadoAndConditions(List<String> conditions) {
        return afipConceptoSueldoSearchRepository.findAllByAsignadoAndConditions(conditions);
    }
}
