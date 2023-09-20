package um.haberes.rest.repository.view;

import um.haberes.rest.kotlin.view.AfipConceptoSueldoSearch;

import java.util.List;

public interface IAfipConceptoSueldoSearchRepositoryCustom {

    public List<AfipConceptoSueldoSearch> findAllByAsignadoAndConditions(List<String> conditions);

}
