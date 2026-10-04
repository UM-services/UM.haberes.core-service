package um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model;

import java.math.BigDecimal;

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
public class DesignacionTipo {

    private Integer designacionTipoId;

    private String nombre;

    private BigDecimal horasSemanales;

    private BigDecimal horasTotales;

    private Integer simples;
}
