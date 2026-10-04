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

import um.haberes.core.hexagonal.cursos.curso.infrastructure.persistence.entity.CursoEntity;
import um.haberes.core.repository.JpaCursoRepositoryCustom;

/**
 * @author daniel
 *
 */
public class JpaCursoRepositoryCustomImpl implements JpaCursoRepositoryCustom {

	private final EntityManager entityManager;

	@Autowired
	public JpaCursoRepositoryCustomImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<CursoEntity> findAllByFacultadIdAndGeograficaIdAndConditions(Integer facultadId, Integer geograficaId,
																	   List<String> conditions) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CursoEntity> query = criteriaBuilder.createQuery(CursoEntity.class);
		Root<CursoEntity> root = query.from(CursoEntity.class);

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
