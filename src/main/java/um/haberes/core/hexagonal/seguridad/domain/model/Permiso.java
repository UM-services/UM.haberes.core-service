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
public class Permiso {

    private Long permisoId;
    private String clave;
    private String descripcion;
    private String modulo;
    private String aplicacion;
    private Byte activo;
}
