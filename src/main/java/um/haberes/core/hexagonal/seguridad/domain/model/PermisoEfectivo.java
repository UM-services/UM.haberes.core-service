package um.haberes.core.hexagonal.seguridad.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermisoEfectivo {

    private Long legajoId;
    /** Claves de catálogo efectivas del usuario, ordenadas y sin repetir. */
    private List<String> permisos;
    /** true si el usuario tiene el flag administrador (acceso total vía ADMIN_HABERES). */
    private boolean administrador;
}
