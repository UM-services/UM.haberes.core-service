/**
 * 
 */
package ar.edu.um.haberes.rest.repository.impl;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.um.haberes.rest.model.Curso;
import ar.edu.um.haberes.rest.repository.ICursoRepositoryCustom;

/**
 * @author daniel
 *
 */
public class ICursoRepositoryCustomImpl implements ICursoRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Curso> findAllByFacultadIdAndGeograficaIdAndConditions(Integer facultadId, Integer geograficaId,
			List<String> conditions) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Curso> query = criteriaBuilder.createQuery(Curso.class);
		Root<Curso> root = query.from(Curso.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(criteriaBuilder.equal(root.get("facultadId"), facultadId));
		predicates.add(criteriaBuilder.equal(root.get("geograficaId"), geograficaId));
		conditions.forEach(condition -> {
			predicates.add(criteriaBuilder.like(root.get("nombre"), "%" + condition + "%"));
		});
		query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
		query.orderBy(criteriaBuilder.asc(root.get("nombre")));
		return entityManager.createQuery(query).getResultList();
	}

}
