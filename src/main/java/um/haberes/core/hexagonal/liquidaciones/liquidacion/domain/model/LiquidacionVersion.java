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
public class LiquidacionVersion {

    private Long liquidacionVersionId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private Integer version;

    private OffsetDateTime fechaLiquidacion;

    private OffsetDateTime fechaAcreditacion;

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

    @Builder.Default
    private Byte bloqueado = 0;

    @Builder.Default
    private Integer estado = 0;

    @Builder.Default
    private String liquida = "";
}
