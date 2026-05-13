package um.haberes.core.hexagonal.geografica.infrastructure.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class GeograficaResponse {
    private Integer geograficaId;
    private String nombre;
    private String reducido;
    private BigDecimal desarraigo;
    private Integer geograficaIdReemplazo;
}
