package um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.facultad.infrastructure.web.dto.FacultadResponse;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.dto.PersonaResponse;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaFacultadResponse {

    private Long personaFacultadId;

    private Long legajoId;

    private Integer facultadId;

    private PersonaResponse persona;

    private FacultadResponse facultad;
}
