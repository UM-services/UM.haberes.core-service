package um.haberes.core.repository.view.impl;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.persistence.entity.NovedadEntity;
import um.haberes.core.model.view.NovedadDuplicada;
import um.haberes.core.repository.view.JpaNovedadDuplicadaRepositoryCustom;

/**
 * Reemplaza la lectura de {@code vw_novedad_duplicada} por una consulta Criteria sobre
 * {@code novedad}, con el período como filtro previo al {@code group by}. La vista agrupaba las
 * 377k filas de la tabla para cada liquidación; esta versión resuelve sólo el período consultado.
 *
 * <p>Excluye las novedades con {@code codigo_id NULL}: el único de {@code novedad} permite varias
 * filas nulas y son válidas, no duplicados.</p>
 */
@RequiredArgsConstructor
public class JpaNovedadDuplicadaRepositoryCustomImpl implements JpaNovedadDuplicadaRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public List<NovedadDuplicada> findAllByPeriodo(Integer anho, Integer mes) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
        Root<NovedadEntity> root = query.from(NovedadEntity.class);

        query.multiselect(
                root.get("legajoId"),
                root.get("anho"),
                root.get("mes"),
                root.get("codigoId"),
                criteriaBuilder.count(root));
        query.where(
                criteriaBuilder.equal(root.get("anho"), anho),
                criteriaBuilder.equal(root.get("mes"), mes),
                criteriaBuilder.isNull(root.get("dependenciaId")),
                // codigo_id es nullable y el único de novedad permite varias filas con NULL:
                // esas novedades son válidas y no deben tratarse como duplicadas.
                criteriaBuilder.isNotNull(root.get("codigoId")));
        query.groupBy(
                root.get("legajoId"),
                root.get("anho"),
                root.get("mes"),
                root.get("codigoId"));
        query.having(criteriaBuilder.gt(criteriaBuilder.count(root), 1L));

        List<NovedadDuplicada> duplicadas = new ArrayList<>();
        for (Object[] fila : entityManager.createQuery(query).getResultList()) {
            Long legajoId = (Long) fila[0];
            Integer anhoFila = (Integer) fila[1];
            Integer mesFila = (Integer) fila[2];
            Integer codigoId = (Integer) fila[3];
            Number cantidad = (Number) fila[4];
            duplicadas.add(new NovedadDuplicada(
                    legajoId + "." + anhoFila + "." + mesFila + "." + codigoId,
                    legajoId, anhoFila, mesFila, codigoId, cantidad.intValue()));
        }
        return duplicadas;
    }
}
