package um.haberes.core.hexagonal.cursos.cargo_tipo.domain.model;

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
public class CargoTipo {

    private Integer cargoTipoId;

    @Builder.Default
    private Byte aCargo = 0;

    @Builder.Default
    private String nombre = "";

    @Builder.Default
    private int precedencia = 0;
}
