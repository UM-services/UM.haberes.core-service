package um.haberes.core.hexagonal.liquidaciones.sijp.domain.model;

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
public class LegajoOmitidoSijp {

    private Long legajoId;

    private String apellidoNombre;

    private String motivo;

}
