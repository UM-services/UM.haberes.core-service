package um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.web.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Tramo de la escala de arraigo ETEC. La escala se envía completa al reemplazarla. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EscalaArraigoEtecRequest {

    private Integer aniosMinimos;

    private BigDecimal porcentaje;
}
