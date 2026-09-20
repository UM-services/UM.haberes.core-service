package um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.web.dto;

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
public class NovedadRequest {

    @NotNull
    private Long legajoId;

    @NotNull
    private Integer anho;

    @NotNull
    private Integer mes;

    @NotNull
    private Integer codigoId;

    private Integer dependenciaId;

    private BigDecimal importe;

    private String value;

    private String observaciones;

    private Byte importado;

    private Long novedadUploadId;
}
