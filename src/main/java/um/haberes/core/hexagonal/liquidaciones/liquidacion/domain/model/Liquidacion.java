package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Liquidacion {

    private Long liquidacionId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

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

    private Persona persona;

    public String key() {
        return String.valueOf(legajoId) + "." + anho + "." + mes;
    }
}
