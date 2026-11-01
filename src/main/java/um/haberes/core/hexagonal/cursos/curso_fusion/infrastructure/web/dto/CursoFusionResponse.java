package um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.web.dto;

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
}
