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
public class RolResponse {

    private Long rolId;
    private String nombre;
    private String descripcion;
    private String aplicacion;
    private Byte activo;
    private boolean protegido;
}
