package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto.CategoriaResponse;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.dto.DependenciaResponse;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.dto.PersonaResponse;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargoLiquidacionResponse {

    private Long cargoLiquidacionId;

    private Long legajoId;

    private PersonaResponse persona;

    private Integer anho;

    private Integer mes;

    private Integer dependenciaId;

    private DependenciaResponse dependencia;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaDesde;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaHasta;

    private Integer categoriaId;

    private String categoriaNombre;

    private BigDecimal categoriaBasico;

    private CategoriaResponse categoria;

    private BigDecimal estadoDocente;

    private BigDecimal horasJornada;

    private int jornada;

    private int presentismo;

    private String situacion;
}
