package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.UniversoCargos;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

/**
 * Traduce un universo de cargos a las categorías que le pertenecen. Es el único lugar donde se
 * define el alcance de borrado de cada universo:
 *
 * <ul>
 *   <li>DOCENTE: {@code docente = 1} y {@code puntos = 0}</li>
 *   <li>NO_DOCENTE: {@code noDocente = 1} y {@code puntos = 0}</li>
 *   <li>ETEC: {@code puntos > 0} más la categoría de horas de clase {@code 2002}</li>
 * </ul>
 *
 * <p>Los universos son disjuntos por construcción: una categoría ETEC nunca entra en el borrado
 * docente/no docente, y viceversa. La categoría {@code 2002 DOCENTE ETEC HORAS} no tiene puntos;
 * se agrega explícitamente al universo ETEC porque sus cargos los genera la escala de horas.</p>
 */
@Component
@RequiredArgsConstructor
public class CatalogoUniversosCargos {

    /** Categoría del cargo que genera la escala de horas de clase ETEC. */
    public static final Integer CATEGORIA_HORAS_ETEC = 2002;

    private static final byte ACTIVO = 1;

    private final CategoriaRepository categoriaRepository;

    /** Una categoría es ETEC cuando tiene puntos configurados o es la de horas de clase. */
    public static boolean esCategoriaEtec(Categoria categoria) {
        return esCategoriaHorasEtec(categoria)
                || (categoria != null && categoria.getPuntos() != null
                        && categoria.getPuntos().compareTo(BigDecimal.ZERO) > 0);
    }

    /** La categoría de horas de clase ETEC liquida por la escala de horas, no por puntos. */
    public static boolean esCategoriaHorasEtec(Categoria categoria) {
        return categoria != null && CATEGORIA_HORAS_ETEC.equals(categoria.getCategoriaId());
    }

    public List<Integer> categoriaIds(Set<UniversoCargos> universos, List<Integer> categoriasAdicionales) {
        List<Integer> categorias = new ArrayList<>(universos.stream()
                .flatMap(universo -> categoriaIds(universo).stream())
                .toList());
        if (categoriasAdicionales != null) {
            categorias.addAll(categoriasAdicionales);
        }
        return categorias.stream().distinct().toList();
    }

    public List<Integer> categoriaIds(UniversoCargos universo) {
        return switch (universo) {
            case DOCENTE -> idsComunes(categoriaRepository.findAllByDocente(ACTIVO));
            case NO_DOCENTE -> idsComunes(categoriaRepository.findAllByNoDocente(ACTIVO));
            case ETEC -> idsEtec(categoriaRepository.findAllByPuntosGreaterThan(BigDecimal.ZERO));
        };
    }

    private List<Integer> idsEtec(List<Categoria> categorias) {
        List<Integer> ids = new ArrayList<>(ids(categorias));
        ids.add(CATEGORIA_HORAS_ETEC);
        return ids.stream().distinct().toList();
    }

    private List<Integer> idsComunes(List<Categoria> categorias) {
        return ids(categorias.stream().filter(categoria -> !esCategoriaEtec(categoria)).toList());
    }

    private List<Integer> ids(List<Categoria> categorias) {
        return categorias.stream()
                .map(Categoria::getCategoriaId)
                .filter(categoriaId -> categoriaId != null)
                .toList();
    }
}
