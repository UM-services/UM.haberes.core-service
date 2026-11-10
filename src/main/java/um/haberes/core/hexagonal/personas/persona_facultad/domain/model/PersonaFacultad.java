package um.haberes.core.hexagonal.personas.persona_facultad.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaFacultad {

    private Long personaFacultadId;

    private Long legajoId;

    private Integer facultadId;

    private Persona persona;

    private Facultad facultad;
}
