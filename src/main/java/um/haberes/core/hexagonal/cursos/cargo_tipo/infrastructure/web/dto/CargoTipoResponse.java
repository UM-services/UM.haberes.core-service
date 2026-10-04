package um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.web.dto;

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
public class CargoTipoResponse {

    private Integer cargoTipoId;

    private Byte aCargo;

    private String nombre;

    private int precedencia;
}
