package um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.web.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.web.dto.CargoTipoResponse;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.web.dto.CursoResponse;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.web.dto.DesignacionTipoResponse;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto.CategoriaResponse;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.dto.PersonaResponse;

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

    private CursoResponse curso;

    private CargoTipoResponse cargoTipo;

    private PersonaResponse persona;

    private DesignacionTipoResponse designacionTipo;

    private CategoriaResponse categoria;
}
