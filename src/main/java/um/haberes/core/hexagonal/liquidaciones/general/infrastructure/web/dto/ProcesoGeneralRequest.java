package um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Pedido de un proceso masivo: sólo la confirmación de forzar el bloqueo. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcesoGeneralRequest {

    private Boolean forzar;
}
