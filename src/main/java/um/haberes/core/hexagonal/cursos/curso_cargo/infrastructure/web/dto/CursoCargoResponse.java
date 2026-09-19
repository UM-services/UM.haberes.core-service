package um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.web.dto;

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
public class CursoCargoResponse {

    private Long cursoCargoId;

    private Long cursoId;

    private int anho;

    private int mes;

    private Integer cargoTipoId;

    private Long legajoId;

    private BigDecimal horasSemanales;

    private BigDecimal horasTotales;

    private Integer designacionTipoId;

    private Integer categoriaId;

    private Byte desarraigo;

    private Long cursoCargoNovedadId;
}
