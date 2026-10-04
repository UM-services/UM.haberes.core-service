package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

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
public class LiquidacionPeriodoForward {

    private Long liquidacionId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private OffsetDateTime fechaLiquidacion;

    private Integer dependenciaId;

    private String salida;

    @Builder.Default
    private BigDecimal totalRemunerativo = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal totalNoRemunerativo = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal totalDeduccion = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal totalNeto = BigDecimal.ZERO;

    private Byte bloqueado;

    private Long periodo;
}
