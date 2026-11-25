package um.haberes.core.hexagonal.seguridad.domain.model;

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
public class UsuarioRol {

    private Long usuarioRolId;
    private Long legajoId;
    private Long rolId;
}
