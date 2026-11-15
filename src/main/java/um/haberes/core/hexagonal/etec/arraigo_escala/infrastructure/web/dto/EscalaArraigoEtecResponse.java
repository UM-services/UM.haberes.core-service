package um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.web.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Tramo de la escala de arraigo ETEC tal como lo devuelve el API. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EscalaArraigoEtecResponse {

    private Long escalaArraigoEtecId;

    private Integer aniosMinimos;

    private BigDecimal porcentaje;
}
