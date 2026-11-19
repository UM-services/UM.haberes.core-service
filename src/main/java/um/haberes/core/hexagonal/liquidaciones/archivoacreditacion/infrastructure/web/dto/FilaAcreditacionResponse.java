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
public class FilaAcreditacionResponse {

    private Long legajoBancoId;

    private Long legajoId;

    private String cbu;

    private BigDecimal fijo;

    private BigDecimal porcentaje;

    private Byte resto;

    private BigDecimal acreditado;

    private String apellidoNombre;

    private String cuil;
}
