package um.haberes.core.hexagonal.seguridad.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncResultado {

    @Builder.Default
    private List<String> creados = new ArrayList<>();
    @Builder.Default
    private List<String> actualizados = new ArrayList<>();
    @Builder.Default
    private List<String> retirados = new ArrayList<>();
    @Builder.Default
    private List<String> asignacionesAgregadas = new ArrayList<>();
    @Builder.Default
    private List<String> asignacionesQuitadas = new ArrayList<>();
    @Builder.Default
    private List<String> warnings = new ArrayList<>();

    public int totalCambios() {
        return creados.size() + actualizados.size() + retirados.size()
                + asignacionesAgregadas.size() + asignacionesQuitadas.size();
    }
}
