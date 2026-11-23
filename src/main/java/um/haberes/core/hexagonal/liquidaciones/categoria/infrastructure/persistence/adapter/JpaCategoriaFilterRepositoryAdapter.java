package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaFilter;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaPage;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CondicionCategoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaFilterRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.entity.CategoriaEntity;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.mapper.CategoriaMapper;

/**
 * Búsqueda paginada del catálogo con Criteria API (sin SQL textual ni {@code @Query}).
 * El texto se combina con AND entre palabras; las condiciones, con OR.
 */
@Component
@RequiredArgsConstructor
public class JpaCategoriaFilterRepositoryAdapter implements CategoriaFilterRepository {

    private static final byte ACTIVO = 1;

    private final EntityManager entityManager;
    private final CategoriaMapper categoriaMapper;

    @Override
    public CategoriaPage findByFilter(CategoriaFilter filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<CategoriaEntity> query = criteriaBuilder.createQuery(CategoriaEntity.class);
        Root<CategoriaEntity> root = query.from(CategoriaEntity.class);
        query.select(root)
                .where(predicados(criteriaBuilder, root, filter))
                .orderBy(criteriaBuilder.asc(root.get("categoriaId")));

        List<CategoriaEntity> entities = entityManager.createQuery(query)
                .setFirstResult(filter.getPagina() * filter.getTamano())
                .setMaxResults(filter.getTamano())
                .getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<CategoriaEntity> countRoot = countQuery.from(CategoriaEntity.class);
        countQuery.select(criteriaBuilder.count(countRoot))
                .where(predicados(criteriaBuilder, countRoot, filter));
        long total = entityManager.createQuery(countQuery).getSingleResult();

        List<Categoria> filas = entities.stream()
                .map(categoriaMapper::toDomain)
                .collect(Collectors.toList());

        return CategoriaPage.builder()
                .filas(filas)
                .total(total)
                .pagina(filter.getPagina())
                .tamano(filter.getTamano())
                .build();
    }

    private Predicate predicados(CriteriaBuilder criteriaBuilder, Root<CategoriaEntity> root,
            CategoriaFilter filter) {
        List<Predicate> partes = new ArrayList<>();

        List<Predicate> texto = predicadosTexto(criteriaBuilder, root, filter.getTexto());
        if (!texto.isEmpty()) {
            partes.add(criteriaBuilder.and(texto.toArray(Predicate[]::new)));
        }

        if (filter.getCondiciones() != null && !filter.getCondiciones().isEmpty()) {
            List<Predicate> condiciones = filter.getCondiciones().stream()
                    .map(condicion -> predicadoCondicion(criteriaBuilder, root, condicion))
                    .collect(Collectors.toList());
            partes.add(criteriaBuilder.or(condiciones.toArray(Predicate[]::new)));
        }

        if (partes.isEmpty()) {
            return criteriaBuilder.conjunction();
        }
        return criteriaBuilder.and(partes.toArray(Predicate[]::new));
    }

    private List<Predicate> predicadosTexto(CriteriaBuilder criteriaBuilder, Root<CategoriaEntity> root,
            String texto) {
        List<Predicate> predicados = new ArrayList<>();
        if (texto == null || texto.isBlank()) {
            return predicados;
        }

        String limpio = texto.trim();
        List<Predicate> palabras = new ArrayList<>();
        for (String palabra : limpio.split("\\s+")) {
            if (palabra.isBlank()) {
                continue;
            }
            String patron = "%" + palabra.toUpperCase(Locale.ROOT) + "%";
            palabras.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("nombre")), patron));
        }

        // Multi-palabra: todas las palabras tienen que aparecer en el nombre.
        Predicate porNombre = criteriaBuilder.and(palabras.toArray(Predicate[]::new));

        // Búsqueda por ID sólo cuando todo el texto es numérico (evita que un dígito
        // suelto traiga cualquier ID que lo contenga).
        if (esNumerica(limpio)) {
            Predicate porId = criteriaBuilder.like(root.get("categoriaId").as(String.class),
                    "%" + limpio + "%");
            predicados.add(criteriaBuilder.or(porNombre, porId));
        } else {
            predicados.add(porNombre);
        }
        return predicados;
    }

    private Predicate predicadoCondicion(CriteriaBuilder criteriaBuilder, Root<CategoriaEntity> root,
            CondicionCategoria condicion) {
        return switch (condicion) {
            case DOCENTE -> criteriaBuilder.equal(root.get("docente"), ACTIVO);
            case NO_DOCENTE -> criteriaBuilder.equal(root.get("noDocente"), ACTIVO);
            case LIQUIDA_POR_HORA -> criteriaBuilder.equal(root.get("liquidaPorHora"), ACTIVO);
            case ETEC -> criteriaBuilder.greaterThan(root.get("puntos"), BigDecimal.ZERO);
        };
    }

    private boolean esNumerica(String palabra) {
        return palabra.chars().allMatch(Character::isDigit);
    }
}
