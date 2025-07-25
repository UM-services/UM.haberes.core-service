/**
 * 
 */
package um.haberes.core.repository.impl;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import um.haberes.core.kotlin.model.Curso;
import um.haberes.core.repository.CursoRepositoryCustom;

/**
 * @author daniel
 *
 */
public class CursoRepositoryCustomImpl implements CursoRepositoryCustom {

	private final EntityManager entityManager;

	@Autowired
	public CursoRepositoryCustomImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

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
