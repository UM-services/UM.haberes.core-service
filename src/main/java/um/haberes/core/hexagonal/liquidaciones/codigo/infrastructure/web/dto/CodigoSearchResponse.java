package um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.dto;

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
public class CodigoSearchResponse {

    private Integer codigoId;

    private String nombre;

    private Byte docente;

    private Byte noDocente;

    private String search;
}
