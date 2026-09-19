package um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.web.dto;

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
public class LetraRequest {

    @NotNull
    private Long legajoId;

    @NotNull
    private Integer anho;

    @NotNull
    private Integer mes;

    private BigDecimal neto;

    private String cadena;
}
