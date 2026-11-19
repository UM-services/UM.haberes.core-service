package um.haberes.core.hexagonal.liquidaciones.sijp.domain.model;

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
public class ArchivoSijp {

    private VarianteSijp variante;

    private String nombreArchivo;

    private byte[] contenido;

    private int cantidad;

    @Builder.Default
    private List<LegajoOmitidoSijp> omitidos = new ArrayList<>();

    @Builder.Default
    private List<String> advertencias = new ArrayList<>();

}
