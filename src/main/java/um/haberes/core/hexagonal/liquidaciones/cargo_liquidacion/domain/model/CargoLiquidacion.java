package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargoLiquidacion {

    private Long cargoLiquidacionId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private Integer dependenciaId;

    private OffsetDateTime fechaDesde;

    private OffsetDateTime fechaHasta;

    private Integer categoriaId;

    @Builder.Default
    private String categoriaNombre = "";

    @Builder.Default
    private BigDecimal categoriaBasico = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal estadoDocente = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal horasJornada = BigDecimal.ZERO;

    @Builder.Default
    private int jornada = 0;

    @Builder.Default
    private int presentismo = 0;

    private String situacion;

    private Dependencia dependencia;

    private Categoria categoria;
}
