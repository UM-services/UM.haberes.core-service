package um.haberes.core.repository.view;

import um.haberes.core.kotlin.model.view.AfipConceptoSueldoSearch;

import java.util.List;

public interface IAfipConceptoSueldoSearchRepositoryCustom {

    public List<AfipConceptoSueldoSearch> findAllByAsignadoAndConditions(List<String> conditions);

}
