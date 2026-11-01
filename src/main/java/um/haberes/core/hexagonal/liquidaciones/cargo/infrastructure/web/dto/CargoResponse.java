package um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class CargoResponse {

    private Long cargoId;

    private Long legajoId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaAlta;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaBaja;

    private Integer dependenciaId;

    private Integer categoriaId;

    private int jornada;

    private int presentismo;

    private BigDecimal horasJornada;

    private String dependenciaNombre;

    private String categoriaNombre;

    private String personaApellido;

    private String personaNombre;
}
