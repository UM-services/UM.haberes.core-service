package um.haberes.core.hexagonal.personas.dependencia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;

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

    private Facultad facultad;

    private Geografica geografica;

    public String getSedeKey() {
        return String.valueOf(facultadId) + "." + geograficaId;
    }
}
