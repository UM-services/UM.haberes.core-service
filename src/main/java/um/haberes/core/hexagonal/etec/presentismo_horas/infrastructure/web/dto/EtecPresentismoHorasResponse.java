package um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.web.dto;

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
public class EtecPresentismoHorasResponse {

    private Long presentismoHorasId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private String motivo;

    private String personaApellido;

    private String personaNombre;

    /** El legajo tiene cursos ETEC con horas en el período. */
    private boolean tieneCargosHoras;
}
