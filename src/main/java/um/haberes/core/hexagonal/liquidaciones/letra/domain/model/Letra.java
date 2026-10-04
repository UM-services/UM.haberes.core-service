package um.haberes.core.hexagonal.liquidaciones.letra.domain.model;

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
public class Letra {

    private Long letraId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    @Builder.Default
    private BigDecimal neto = BigDecimal.ZERO;

    @Builder.Default
    private String cadena = "";
}
