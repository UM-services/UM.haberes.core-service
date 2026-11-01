package um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model;

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
public class CursoDesarraigo {

    private Long cursoDesarraigoId;

    private Long legajoId;

    private int anho;

    private Integer mes;

    private Long cursoId;

    private Integer geograficaId;

    @Builder.Default
    private BigDecimal importe = BigDecimal.ZERO;

    private Integer version;
}
