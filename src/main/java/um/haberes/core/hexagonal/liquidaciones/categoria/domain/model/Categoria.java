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
public class Categoria {

    private Integer categoriaId;

    @Builder.Default
    private String nombre = "";

    @Builder.Default
    private BigDecimal basico = BigDecimal.ZERO;

    @Builder.Default
    private Byte docente = 0;

    @Builder.Default
    private Byte noDocente = 0;

    @Builder.Default
    private Byte liquidaPorHora = 0;

    @Builder.Default
    private BigDecimal estadoDocente = BigDecimal.ZERO;
}
