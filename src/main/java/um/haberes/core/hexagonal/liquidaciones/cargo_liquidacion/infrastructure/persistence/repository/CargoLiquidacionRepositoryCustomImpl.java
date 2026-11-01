package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.entity.CargoLiquidacionEntity;

public class CargoLiquidacionRepositoryCustomImpl implements CargoLiquidacionRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public CargoLiquidacionRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CargoLiquidacionEntity> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CargoLiquidacionEntity> query = criteriaBuilder.createQuery(CargoLiquidacionEntity.class);
        Root<CargoLiquidacionEntity> root = query.from(CargoLiquidacionEntity.class);

        root.fetch("categoria", JoinType.LEFT);
        root.fetch("persona", JoinType.LEFT);
        root.fetch("dependencia", JoinType.LEFT);

        query.select(root).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("legajoId"), legajoId),
                        criteriaBuilder.equal(root.get("anho"), anho),
                        criteriaBuilder.equal(root.get("mes"), mes)));
        return entityManager.createQuery(query).getResultList();
    }
}
