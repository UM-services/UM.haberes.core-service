package um.haberes.rest.repository;

import um.haberes.rest.kotlin.model.AfipConceptoSueldo;

import java.util.List;

public interface IAfipConceptoSueldoRepositoryCustom {

    public List<AfipConceptoSueldo> findAllByAsignadoAndConditions(List<String> conditions);

}
