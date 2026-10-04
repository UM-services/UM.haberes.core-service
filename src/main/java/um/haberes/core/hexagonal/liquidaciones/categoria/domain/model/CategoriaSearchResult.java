package um.haberes.core.hexagonal.liquidaciones.categoria.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaSearchResult {

    private Integer categoriaId;

    private String nombre;

    private BigDecimal basico;

    private String search;
}
