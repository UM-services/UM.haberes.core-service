package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.dto;

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
public class RechazoAcreditacionResponse {

    private Long legajoId;

    private String apellidoNombre;

    private String motivo;

    private BigDecimal importe;

    private BigDecimal subtotal;
}
