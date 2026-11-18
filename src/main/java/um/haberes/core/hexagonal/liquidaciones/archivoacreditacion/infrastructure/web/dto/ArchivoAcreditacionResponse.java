package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
public class ArchivoAcreditacionResponse {

    private String nombreArchivo;

    /** Contenido del archivo bancario en Base64 (650 caracteres por línea, CRLF, ANSI). */
    private String contenido;

    private Integer cantidad;

    private BigDecimal total;

    private boolean periodoAcreditado;

    @Builder.Default
    private List<RechazoAcreditacionResponse> rechazos = new ArrayList<>();

    @Builder.Default
    private List<String> advertencias = new ArrayList<>();
}
