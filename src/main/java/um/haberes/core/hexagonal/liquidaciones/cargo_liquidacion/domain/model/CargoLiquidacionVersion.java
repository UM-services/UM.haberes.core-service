package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model;

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
public class CargoLiquidacionVersion {

    private Long cargoLiquidacionVersionId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private Integer version;

    private Integer dependenciaId;

    private Integer categoriaId;

    @Builder.Default
    private BigDecimal basico = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal estadoDocente = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal horasJornada = BigDecimal.ZERO;

    @Builder.Default
    private int jornada = 0;

    @Builder.Default
    private int presentismo = 0;

    private OffsetDateTime fechaDesde;

    private OffsetDateTime fechaHasta;

    private String situacion;
}
