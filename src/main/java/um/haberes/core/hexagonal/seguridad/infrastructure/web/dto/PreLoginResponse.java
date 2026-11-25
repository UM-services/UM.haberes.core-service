package um.haberes.core.hexagonal.seguridad.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Datos mínimos que necesita la pantalla de login antes de autenticar: nombre
 * para mostrar y los dos flags que cambian el formulario. No expone documento,
 * CUIL, facultad, hash de clave ni permisos.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreLoginResponse {

    private Long legajoId;
    private String apellidoNombre;
    private boolean habilitado;
    private boolean primerIngreso;
}
