package um.haberes.core.hexagonal.geografica.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Geografica {
    private Integer geograficaId;
    private String nombre;
    private String reducido;
    private BigDecimal desarraigo;
    private Integer geograficaIdReemplazo;
}
