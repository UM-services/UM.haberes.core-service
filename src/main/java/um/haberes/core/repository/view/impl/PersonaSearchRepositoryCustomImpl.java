/**
 * 
 */
package um.haberes.core.repository.view.impl;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import um.haberes.core.kotlin.model.view.PersonaSearch;
import um.haberes.core.repository.view.PersonaSearchRepositoryCustom;

/**
 * @author daniel
 *
 */
public class PersonaSearchRepositoryCustomImpl implements PersonaSearchRepositoryCustom {

	private final EntityManager entityManager;

	public PersonaSearchRepositoryCustomImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<PersonaSearch> findAllByStrings(List<String> conditions) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PersonaSearch> query = criteriaBuilder.createQuery(PersonaSearch.class);
		Root<PersonaSearch> root = query.from(PersonaSearch.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		conditions.forEach(condition -> {
			predicates.add(criteriaBuilder.like(root.get("search"), "%" + condition + "%"));
		});
		query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
		query.orderBy(criteriaBuilder.asc(root.get("apellido")), criteriaBuilder.asc(root.get("nombre")));
		return entityManager.createQuery(query).setMaxResults(50).getResultList();
	}

}
