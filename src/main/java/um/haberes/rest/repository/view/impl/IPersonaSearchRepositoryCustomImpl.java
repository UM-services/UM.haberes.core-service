/**
 * 
 */
package um.haberes.rest.repository.view.impl;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import um.haberes.rest.kotlin.model.view.PersonaSearch;
import um.haberes.rest.repository.view.IPersonaSearchRepositoryCustom;

/**
 * @author daniel
 *
 */
public class IPersonaSearchRepositoryCustomImpl implements IPersonaSearchRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

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
