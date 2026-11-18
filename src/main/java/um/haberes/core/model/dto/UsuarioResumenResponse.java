package um.haberes.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Vista de usuario para listados administrativos: omite el hash de la clave.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResumenResponse {

    private Long legajoId;

    private Long usuarioId;

    private Integer facultadId;

    private Byte administrador;
}
