package um.haberes.core.hexagonal.liquidaciones.categoria.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Filtro paginado del catálogo de categorías. {@code texto} busca por nombre y por ID;
 * {@code condiciones} se combinan con OR y con el texto con AND.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaFilter {

    public static final int TAMANO_POR_DEFECTO = 50;

    public static final int TAMANO_MAXIMO = 200;

    @Builder.Default
    private String texto = "";

    @Builder.Default
    private List<CondicionCategoria> condiciones = List.of();

    @Builder.Default
    private int pagina = 0;

    @Builder.Default
    private int tamano = TAMANO_POR_DEFECTO;
}
