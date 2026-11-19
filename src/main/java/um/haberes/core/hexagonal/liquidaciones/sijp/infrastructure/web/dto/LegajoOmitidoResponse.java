package um.haberes.core.hexagonal.liquidaciones.sijp.infrastructure.web.dto;

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
public class LegajoOmitidoResponse {

    private Long legajoId;

    private String apellidoNombre;

    private String motivo;

}
