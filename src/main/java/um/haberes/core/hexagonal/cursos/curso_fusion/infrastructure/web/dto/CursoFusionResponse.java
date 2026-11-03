package um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.web.dto.CargoTipoResponse;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.web.dto.DesignacionTipoResponse;
import um.haberes.core.hexagonal.facultad.infrastructure.web.dto.FacultadResponse;
import um.haberes.core.hexagonal.geografica.infrastructure.web.dto.GeograficaResponse;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto.CategoriaResponse;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.dto.PersonaResponse;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoFusionResponse {

    private Long cursoFusionId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private Integer facultadId;

    private Integer geograficaId;

    private Integer cargoTipoId;

    private Integer designacionTipoId;

    private Byte anual;

    private Integer categoriaId;

    private PersonaResponse persona;

    private FacultadResponse facultad;

    private GeograficaResponse geografica;

    private CargoTipoResponse cargoTipo;

    private DesignacionTipoResponse designacionTipo;

    private CategoriaResponse categoria;
}
