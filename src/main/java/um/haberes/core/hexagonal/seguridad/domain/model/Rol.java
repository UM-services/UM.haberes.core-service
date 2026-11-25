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
public class Rol {

    private Long rolId;
    private String nombre;
    private String descripcion;
    private String aplicacion;
    private Byte activo;
}
