package um.haberes.core.hexagonal.seguridad.infrastructure.web.dto;

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
public class UsuarioPermisoRequest {

    /** 1 = otorgado, 0 = revocado, null = eliminar el override. */
    private Byte otorgado;
}
