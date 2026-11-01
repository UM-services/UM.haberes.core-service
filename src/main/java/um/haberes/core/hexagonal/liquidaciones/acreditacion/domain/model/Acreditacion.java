package um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model;

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
public class Acreditacion {

    private Long acreditacionId;

    private Integer anho;

    private Integer mes;

    private Byte acreditado;

    private OffsetDateTime limiteNovedades;

    private OffsetDateTime fechaContable;

    private Integer ordenContable;

    private BigDecimal sueldosOriginal;

    private BigDecimal sueldosAjustados;

    private BigDecimal contribucionesPatronales;
}
