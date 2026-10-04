package um.haberes.core.hexagonal.liquidaciones.item.domain.model;

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
public class ItemVersion {

    private Long itemVersionId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private Integer codigoId;

    @Builder.Default
    private String codigoNombre = "";

    @Builder.Default
    private BigDecimal importe = BigDecimal.ZERO;
}
