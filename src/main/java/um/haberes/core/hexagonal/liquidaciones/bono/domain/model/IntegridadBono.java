package um.haberes.core.hexagonal.liquidaciones.bono.domain.model;

import java.util.ArrayList;
import java.util.List;

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
public class IntegridadBono {

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    @Builder.Default
    private boolean ok = false;

    @Builder.Default
    private List<FaltanteBono> faltantes = new ArrayList<>();
}
