package um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.dto;

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
public class DependenciaResponse {

    private Integer dependenciaId;

    private String nombre;

    private String acronimo;

    private Integer facultadId;

    private Integer geograficaId;

    public String getSedeKey() {
        return String.valueOf(facultadId) + "." + geograficaId;
    }
}
