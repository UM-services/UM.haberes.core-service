package um.haberes.core.hexagonal.cursos.curso_fusion.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.model.CargoTipo;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model.DesignacionTipo;
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoFusion {

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

    private Persona persona;

    private Facultad facultad;

    private Geografica geografica;

    private CargoTipo cargoTipo;

    private DesignacionTipo designacionTipo;

    private Categoria categoria;
}
