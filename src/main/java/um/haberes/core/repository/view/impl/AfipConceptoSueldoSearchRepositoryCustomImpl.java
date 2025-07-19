package um.haberes.core.repository.view.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import um.haberes.core.kotlin.model.view.AfipConceptoSueldoSearch;
import um.haberes.core.repository.view.AfipConceptoSueldoSearchRepositoryCustom;

import java.util.ArrayList;
import java.util.List;

public class AfipConceptoSueldoSearchRepositoryCustomImpl implements AfipConceptoSueldoSearchRepositoryCustom {

    private final EntityManager entityManager;

    public AfipConceptoSueldoSearchRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<AfipConceptoSueldoSearch> findAllByAsignadoAndConditions(List<String> conditions) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AfipConceptoSueldoSearch> query = criteriaBuilder.createQuery(AfipConceptoSueldoSearch.class);
        Root<AfipConceptoSueldoSearch> root = query.from(AfipConceptoSueldoSearch.class);

        List<Predicate> predicates = new ArrayList<Predicate>();
        conditions.forEach(condition -> {
            predicates.add(criteriaBuilder.like(root.get("search"), "%" + condition + "%"));
        });
        query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
        query.orderBy(criteriaBuilder.asc(root.get("search")));
        return entityManager.createQuery(query).setMaxResults(50).getResultList();
    }
}
