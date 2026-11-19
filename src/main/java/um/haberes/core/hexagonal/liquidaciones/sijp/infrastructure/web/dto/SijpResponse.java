package um.haberes.core.hexagonal.liquidaciones.sijp.infrastructure.web.dto;

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
public class SijpResponse {

    private String nombreArchivo;

    private String variante;

    /** Contenido del archivo SIJP en Base64 (499 caracteres por línea, CRLF, windows-1252). */
    private String contenido;

    private Integer cantidadRegistros;

    @Builder.Default
    private List<LegajoOmitidoResponse> omitidos = new ArrayList<>();

    @Builder.Default
    private List<String> advertencias = new ArrayList<>();

}
