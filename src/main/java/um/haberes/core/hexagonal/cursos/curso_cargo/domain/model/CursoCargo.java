package um.haberes.core.hexagonal.cursos.curso_cargo.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.model.CargoTipo;
import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model.DesignacionTipo;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoCargo {

    private Long cursoCargoId;

    private Long cursoId;

    private int anho;

    private int mes;

    private Integer cargoTipoId;

    private Long legajoId;

    @Builder.Default
    private BigDecimal horasSemanales = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal horasTotales = BigDecimal.ZERO;

    private Integer designacionTipoId;

    private Integer categoriaId;

    @Builder.Default
    private Byte desarraigo = 0;

    private Long cursoCargoNovedadId;

    private Curso curso;

    private CargoTipo cargoTipo;

    private Persona persona;

    private DesignacionTipo designacionTipo;

    private Categoria categoria;
}
