package um.haberes.core.repository.view;

import um.haberes.core.kotlin.model.view.AfipConceptoSueldoSearch;

import java.util.List;

public interface AfipConceptoSueldoSearchRepositoryCustom {

    public List<AfipConceptoSueldoSearch> findAllByAsignadoAndConditions(List<String> conditions);

}
