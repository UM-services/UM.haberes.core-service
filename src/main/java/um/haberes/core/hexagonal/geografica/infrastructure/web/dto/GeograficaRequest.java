package um.haberes.core.hexagonal.geografica.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeograficaRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String reducido;
    private BigDecimal desarraigo;
    private Integer geograficaIdReemplazo;
}
