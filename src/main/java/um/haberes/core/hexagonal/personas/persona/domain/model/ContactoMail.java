package um.haberes.core.hexagonal.personas.persona.domain.model;

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
public class ContactoMail {

    private Long legajoId;

    private String mailPersonal;

    private String mailInstitucional;
}
