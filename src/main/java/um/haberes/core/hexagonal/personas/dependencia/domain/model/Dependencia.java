package um.haberes.core.hexagonal.personas.dependencia.domain.model;

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
public class Dependencia {

    private Integer dependenciaId;

    @Builder.Default
    private String nombre = "";

    @Builder.Default
    private String acronimo = "";

    private Integer facultadId;

    private Integer geograficaId;

    public String getSedeKey() {
        return String.valueOf(facultadId) + "." + geograficaId;
    }
}
