package um.haberes.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.exception.AfipConceptoSueldoException;
import um.haberes.core.model.AfipConceptoSueldoEntity;
import um.haberes.core.model.view.AfipConceptoSueldoSearch;
import um.haberes.core.repository.JpaAfipConceptoSueldoRepository;
import um.haberes.core.repository.view.JpaAfipConceptoSueldoSearchRepository;

import java.util.List;

@Service
public class AfipConceptoSueldoService {

    @Autowired
    private JpaAfipConceptoSueldoRepository repository;

    @Autowired
    private JpaAfipConceptoSueldoSearchRepository afipConceptoSueldoSearchRepository;

    public AfipConceptoSueldoEntity findByAfipConceptoSueldoId(Long afipConceptoSueldoId) {
        return repository.findByAfipConceptoSueldoId(afipConceptoSueldoId).orElseThrow(() -> new AfipConceptoSueldoException(afipConceptoSueldoId));
    }

    public List<AfipConceptoSueldoSearch> findAllByAsignadoAndConditions(List<String> conditions) {
        return afipConceptoSueldoSearchRepository.findAllByAsignadoAndConditions(conditions);
    }
}
