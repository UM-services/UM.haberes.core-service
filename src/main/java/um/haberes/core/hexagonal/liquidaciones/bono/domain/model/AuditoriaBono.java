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
public class AuditoriaBono {

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private Long legajoIdSolicitud;

    private String ipAddress;
}
