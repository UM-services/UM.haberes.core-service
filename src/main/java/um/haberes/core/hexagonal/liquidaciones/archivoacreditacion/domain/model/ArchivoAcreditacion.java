package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model;

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
public class ArchivoAcreditacion {

    private String nombreArchivo;

    private byte[] contenido;

    private int cantidad;

    private BigDecimal total;

    private boolean periodoAcreditado;

    @Builder.Default
    private List<RechazoAcreditacion> rechazos = new ArrayList<>();

    /** Avisos no bloqueantes (por ejemplo, orden de pago no generada). */
    @Builder.Default
    private List<String> advertencias = new ArrayList<>();
}
