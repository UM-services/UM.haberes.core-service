package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.dto;

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
public class LegajoAcreditarRequest {

    @NotNull
    private Long legajoId;

    @NotNull
    private String cbu;

    private BigDecimal acreditado;
}
