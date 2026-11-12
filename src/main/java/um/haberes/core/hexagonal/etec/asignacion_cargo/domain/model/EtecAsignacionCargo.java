package um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Asignación de un cargo con puntos (ETEC) a un legajo. Alta/baja por fechas, igual que
 * {@code cargo} para los cargos no docentes.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtecAsignacionCargo {

    private Long asignacionCargoId;

    private Long legajoId;

    private Integer categoriaId;

    private Integer dependenciaId;

    private OffsetDateTime fechaAlta;

    private OffsetDateTime fechaBaja;

    private String observaciones;

    private String categoriaNombre;

    private String dependenciaNombre;

    private String personaApellido;

    private String personaNombre;
}
