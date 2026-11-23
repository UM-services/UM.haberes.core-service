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
 *   <li>DOCENTE: {@code docente = 1} y no ETEC</li>
 *   <li>NO_DOCENTE: {@code noDocente = 1} y no ETEC</li>
 *   <li>ETEC: {@code puntos > 0} más las categorías marcadas como {@code etec_horas}</li>
 * </ul>
 *
 * <p>La clasificación usa los valores del período ({@code categoria_periodo}), con fallback a la
 * plantilla de {@code categoria} cuando el período no tiene fila. Así una categoría que en el
 * período liquidaba por puntos u horas no queda fuera del alcance de borrado porque la plantilla
 * actual cambió.</p>
 *
 * <p>Los universos son disjuntos por construcción: una categoría ETEC nunca entra en el borrado
 * docente/no docente, y viceversa.</p>
 */
@Component
@RequiredArgsConstructor
public class CatalogoUniversosCargos {

    private static final byte ACTIVO = 1;

    private final CategoriaRepository categoriaRepository;

    /** Una categoría es ETEC cuando tiene puntos configurados o liquida horas de clase. */
    public static boolean esCategoriaEtec(Categoria categoria) {
        return esCategoriaHorasEtec(categoria)
                || (categoria != null && categoria.getPuntos() != null
                        && categoria.getPuntos().compareTo(BigDecimal.ZERO) > 0);
    }

    /** La categoría marcada como «horas ETEC» liquida por la escala de horas, no por puntos. */
    public static boolean esCategoriaHorasEtec(Categoria categoria) {
        return categoria != null && Byte.valueOf(ACTIVO).equals(categoria.getEtecHoras());
    }

    public List<Integer> categoriaIds(Set<UniversoCargos> universos, List<Integer> categoriasAdicionales,
                                      Integer anho, Integer mes) {
        List<Categoria> categorias = categoriaRepository.findAllByPeriodo(anho, mes);
        List<Integer> ids = new ArrayList<>(universos.stream()
                .flatMap(universo -> categoriaIds(categorias, universo).stream())
                .toList());
        if (categoriasAdicionales != null) {
            ids.addAll(categoriasAdicionales);
        }
        return ids.stream().distinct().toList();
    }

    public List<Integer> categoriaIds(UniversoCargos universo, Integer anho, Integer mes) {
        return categoriaIds(categoriaRepository.findAllByPeriodo(anho, mes), universo);
    }

    private List<Integer> categoriaIds(List<Categoria> categorias, UniversoCargos universo) {
        return switch (universo) {
            case DOCENTE -> ids(categorias.stream()
                    .filter(categoria -> Byte.valueOf(ACTIVO).equals(categoria.getDocente()))
                    .filter(categoria -> !esCategoriaEtec(categoria))
                    .toList());
            case NO_DOCENTE -> ids(categorias.stream()
                    .filter(categoria -> Byte.valueOf(ACTIVO).equals(categoria.getNoDocente()))
                    .filter(categoria -> !esCategoriaEtec(categoria))
                    .toList());
            case ETEC -> ids(categorias.stream()
                    .filter(CatalogoUniversosCargos::esCategoriaEtec)
                    .toList());
        };
    }

    private List<Integer> ids(List<Categoria> categorias) {
        return categorias.stream()
                .map(Categoria::getCategoriaId)
                .filter(categoriaId -> categoriaId != null)
                .toList();
    }
}
