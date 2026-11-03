package um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.facultad.infrastructure.web.dto.FacultadResponse;
import um.haberes.core.hexagonal.geografica.infrastructure.web.dto.GeograficaResponse;

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

    private FacultadResponse facultad;

    private GeograficaResponse geografica;

    public String getSedeKey() {
        return String.valueOf(facultadId) + "." + geograficaId;
    }
}
