package um.haberes.core.hexagonal.etec.presentismo_horas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Pérdida de presentismo de los cargos ETEC por horas de un legajo en un período.
 *
 * <p>Los cargos por horas (categorías marcadas como {@code etec_horas}) no tienen
 * {@code etec_asignacion_cargo}: se
 * generan desde los cursos de la facultad 15. La fila es por legajo/período y descuenta el
 * presentismo de todos sus cargos por horas.</p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtecPresentismoHoras {

    private Long presentismoHorasId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private String motivo;

    /** Datos derivados de la persona, para la respuesta de la API. */
    private String personaApellido;

    private String personaNombre;
}
