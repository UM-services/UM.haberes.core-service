package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Respuesta paginada de {@code POST /api/haberes/core/categoria/search}. */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaPageResponse {

    private List<CategoriaResponse> filas;

    private long total;

    private int pagina;

    private int tamano;
}
