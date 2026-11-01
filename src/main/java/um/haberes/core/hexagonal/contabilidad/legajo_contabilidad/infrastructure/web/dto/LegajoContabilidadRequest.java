package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.web.dto;

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
public class LegajoContabilidadRequest {

    @NotNull(message = "El legajo es obligatorio")
    private Long legajoId;

    @NotNull(message = "El año es obligatorio")
    private Integer anho;

    @NotNull(message = "El mes es obligatorio")
    private Integer mes;

    private Byte diferencia;

    private BigDecimal remunerativo;

    private BigDecimal noRemunerativo;
}
