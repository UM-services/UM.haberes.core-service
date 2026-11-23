package um.haberes.core.hexagonal.liquidaciones.categoria.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Página de resultados del buscador de categorías. */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaPage {

    private List<Categoria> filas;

    private long total;

    private int pagina;

    private int tamano;
}
