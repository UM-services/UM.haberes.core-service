package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model;

import java.math.BigDecimal;

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
public class RechazoAcreditacion {

    private Long legajoId;

    private String apellidoNombre;

    private String motivo;

    /** Importe que no se acreditó (grilla de Otros Bancos). */
    private BigDecimal importe;

    /** Acumulado de importes rechazados informado por la grilla de Otros Bancos. */
    private BigDecimal subtotal;
}
