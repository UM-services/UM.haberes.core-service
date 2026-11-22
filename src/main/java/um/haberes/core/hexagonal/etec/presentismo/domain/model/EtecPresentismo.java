package um.haberes.core.hexagonal.etec.presentismo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Pérdida de presentismo de una asignación de cargo ETEC en un período.
 *
 * <p>La asignación es la de {@code etec_asignacion_cargo} (cargo con puntos). La existencia
 * del registro significa que ese cargo no liquida el presentismo ETEC del período: el motor
 * emite el 985 con el importe bruto y el 984 por el mismo importe en negativo.</p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtecPresentismo {

    private Long presentismoId;

    private Long asignacionCargoId;

    private Integer anho;

    private Integer mes;

    private String motivo;

    /** Datos derivados de la asignación, para la respuesta de la API. */
    private Long legajoId;

    private Integer categoriaId;

    private Integer dependenciaId;

    private String categoriaNombre;

    private String dependenciaNombre;

    private String personaApellido;

    private String personaNombre;
}
