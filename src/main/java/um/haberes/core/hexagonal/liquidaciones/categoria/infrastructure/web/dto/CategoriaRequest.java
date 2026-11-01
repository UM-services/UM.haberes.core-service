package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
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
public class CategoriaRequest {

    @NotNull
    private Integer categoriaId;

    private String nombre;

    private BigDecimal basico;

    private Byte docente;

    private Byte noDocente;

    private Byte liquidaPorHora;

    private BigDecimal estadoDocente;
}
