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
public class UsuarioPermiso {

    private Long usuarioPermisoId;
    private Long legajoId;
    private Long permisoId;
    private Byte otorgado;
}
