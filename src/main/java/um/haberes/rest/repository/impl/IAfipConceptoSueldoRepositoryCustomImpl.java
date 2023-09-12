package um.haberes.rest.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import um.haberes.rest.kotlin.model.AfipConceptoSueldo;
import um.haberes.rest.model.Curso;
import um.haberes.rest.repository.IAfipConceptoSueldoRepositoryCustom;

import java.util.ArrayList;
import java.util.List;

public class IAfipConceptoSueldoRepositoryCustomImpl implements IAfipConceptoSueldoRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<AfipConceptoSueldo> findAllByAsignadoAndConditions(List<String> conditions) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AfipConceptoSueldo> query = criteriaBuilder.createQuery(AfipConceptoSueldo.class);
        Root<AfipConceptoSueldo> root = query.from(AfipConceptoSueldo.class);

        List<Predicate> predicates = new ArrayList<Predicate>();
        conditions.forEach(condition -> {
            predicates.add(criteriaBuilder.like(root.get("descripcion"), "%" + condition + "%"));
        });
        query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
        query.orderBy(criteriaBuilder.asc(root.get("descripcion")));
        return entityManager.createQuery(query).setMaxResults(50).getResultList();
    }
}
