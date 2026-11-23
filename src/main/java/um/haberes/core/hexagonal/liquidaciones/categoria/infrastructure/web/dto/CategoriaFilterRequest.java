package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CondicionCategoria;

/** Body de {@code POST /api/haberes/core/categoria/search}. */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaFilterRequest {

    /** Texto libre: busca por nombre y por ID. Vacío u omitido = sin filtro. */
    @Size(max = 100)
    private String texto;

    /** Condiciones del catálogo (OR entre sí). Opcional. */
    private List<CondicionCategoria> condiciones;

    @Min(0)
    private Integer pagina;

    @Min(1)
    @Max(200)
    private Integer tamano;
}
