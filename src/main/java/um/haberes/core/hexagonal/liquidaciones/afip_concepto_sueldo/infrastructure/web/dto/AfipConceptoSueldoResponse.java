package um.haberes.core.hexagonal.liquidaciones.afip_concepto_sueldo.infrastructure.web.dto;

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
public class AfipConceptoSueldoResponse {

    private Long afipConceptoSueldoId;

    private String descripcion;

    private Byte asignado;
}
