package um.haberes.core.hexagonal.liquidaciones.item.infrastructure.web.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
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
public class ItemRequest {

    @NotNull
    private Long legajoId;

    @NotNull
    private Integer anho;

    @NotNull
    private Integer mes;

    @NotNull
    private Integer codigoId;

    private String codigoNombre;

    @NotNull
    private BigDecimal importe;
}
