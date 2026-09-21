package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.dto;

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
public class ActividadResponse {

    private Long actividadId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private Byte docente;

    private Byte otras;

    private Byte clases;

    private Integer dependenciaId;
}
