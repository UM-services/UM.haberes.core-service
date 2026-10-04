package um.haberes.core.hexagonal.liquidaciones.bono.domain.model;

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
public class Actividad {

    private Long actividadId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    @Builder.Default
    private Byte docente = 0;

    @Builder.Default
    private Byte otras = 0;

    @Builder.Default
    private Byte clases = 0;

    private Integer dependenciaId;
}
