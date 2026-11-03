package um.haberes.core.hexagonal.liquidaciones.afip_concepto_sueldo.domain.model;

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
public class AfipConceptoSueldo {

    private Long afipConceptoSueldoId;

    @Builder.Default
    private String descripcion = "";

    @Builder.Default
    private Byte asignado = 0;
}
