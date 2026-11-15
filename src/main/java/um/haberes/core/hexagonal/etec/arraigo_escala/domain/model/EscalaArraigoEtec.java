package um.haberes.core.hexagonal.etec.arraigo_escala.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Tramo de la escala de arraigo ETEC: a partir de {@code aniosMinimos} años de antigüedad docente
 * corresponde {@code porcentaje} sobre el valor del cargo.
 *
 * <p>La escala es global (no depende del período). El tramo aplicable es el de mayor
 * {@code aniosMinimos} que no supere la antigüedad del legajo; si no hay ninguno, el arraigo es
 * cero.</p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EscalaArraigoEtec {

    private Long escalaArraigoEtecId;

    private Integer aniosMinimos;

    private BigDecimal porcentaje;
}
