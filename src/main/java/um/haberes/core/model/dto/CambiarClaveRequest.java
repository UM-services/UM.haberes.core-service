package um.haberes.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Payload de {@code PUT /api/haberes/core/usuario/cambiarclave}: cambio de clave
 * con verificacion server-side de la clave anterior. No expone {@code build},
 * {@code usuarioId} ni {@code facultadId}: se preservan los valores actuales.
 *
 * @author dquinteros
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "currentPassword", "newPassword", "reClaveNueva" })
public class CambiarClaveRequest {

    private Long legajoId;

    private String currentPassword;

    private String newPassword;

    private String reClaveNueva;
}
