package um.haberes.core.repository.view;

import um.haberes.core.model.view.AfipConceptoSueldoSearch;

import java.util.List;

public interface JpaAfipConceptoSueldoSearchRepositoryCustom {

    public List<AfipConceptoSueldoSearch> findAllByAsignadoAndConditions(List<String> conditions);

}
