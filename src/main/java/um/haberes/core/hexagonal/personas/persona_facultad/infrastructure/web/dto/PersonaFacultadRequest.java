package um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.web.dto;

import jakarta.validation.constraints.NotNull;
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
public class PersonaFacultadRequest {

    /**
     * Clave que envía el cliente legacy (`clsMODPersonaFacultad.makeParams` en bonos.vb6).
     * Se acepta y se ignora: el alta siempre genera un registro nuevo.
     */
    private Long personaFacultadId;

    @NotNull
    private Long legajoId;

    @NotNull
    private Integer facultadId;
}
