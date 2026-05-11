package um.haberes.core.hexagonal.geografica.infrastructure.web.dto;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeograficaResponse {
    private Integer geograficaId;
    private String nombre;
    private String reducido;
    private BigDecimal desarraigo;
    private Integer geograficaIdReemplazo;
}
