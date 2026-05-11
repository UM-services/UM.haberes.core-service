package um.haberes.core.hexagonal.geografica.infrastructure.web.dto;
import lombok.Data;
import java.math.BigDecimal;
@Data
public class GeograficaRequest {
    private String nombre;
    private String reducido;
    private BigDecimal desarraigo;
    private Integer geograficaIdReemplazo;
}
