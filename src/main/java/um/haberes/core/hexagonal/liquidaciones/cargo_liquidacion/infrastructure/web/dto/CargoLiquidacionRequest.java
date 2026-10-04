package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class CargoLiquidacionRequest {

    @NotNull
    private Long legajoId;

    @NotNull
    private Integer anho;

    @NotNull
    private Integer mes;

    private Integer dependenciaId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaDesde;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaHasta;

    @NotNull
    private Integer categoriaId;

    private String categoriaNombre;

    private BigDecimal categoriaBasico;

    private BigDecimal estadoDocente;

    private BigDecimal horasJornada;

    private int jornada;

    private int presentismo;

    private String situacion;
}
