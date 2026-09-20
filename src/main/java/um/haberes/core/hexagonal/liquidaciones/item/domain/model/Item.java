package um.haberes.core.hexagonal.liquidaciones.item.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.util.Jsonifyable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Jsonifyable {

    private Long itemId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private Integer codigoId;

    @Builder.Default
    private String codigoNombre = "";

    @Builder.Default
    private BigDecimal importe = BigDecimal.ZERO;

    private Byte codigoIncluidoEtec;

    private Codigo codigo;

    public String legajoKey() {
        return String.valueOf(legajoId) + "." + anho + "." + mes + "." + codigoId;
    }

}
