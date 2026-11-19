package um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Alta o actualización de una novedad del legajo/período. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarNovedadIndividualRequest {

    @NotNull
    private Integer codigoId;

    private BigDecimal importe;

    /** {@code "S"} o {@code "N"} para novedades sin importe; el backend normaliza el par. */
    private String valor;

    private String observaciones;

    private Boolean forzar;
}
