package um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.web.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
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
public class CursoCargoRequest {

    @NotNull
    private Long cursoId;

    @NotNull
    private Integer anho;

    @NotNull
    private Integer mes;

    private Integer cargoTipoId;

    @NotNull
    private Long legajoId;

    private BigDecimal horasSemanales;

    private BigDecimal horasTotales;

    private Integer designacionTipoId;

    private Integer categoriaId;

    private Byte desarraigo;

    private Long cursoCargoNovedadId;
}
