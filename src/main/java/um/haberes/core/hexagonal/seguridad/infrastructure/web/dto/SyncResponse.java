package um.haberes.core.hexagonal.seguridad.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncResponse {

    private List<String> creados;
    private List<String> actualizados;
    private List<String> retirados;
    private List<String> asignacionesAgregadas;
    private List<String> asignacionesQuitadas;
    private List<String> warnings;
    private int totalCambios;
}
