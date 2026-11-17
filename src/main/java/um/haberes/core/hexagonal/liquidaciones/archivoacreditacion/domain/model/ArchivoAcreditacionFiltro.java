package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model;

import java.time.LocalDate;
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
public class ArchivoAcreditacionFiltro {

    private Integer anho;

    private Integer mes;

    private LocalDate fechaPago;

    @Builder.Default
    private Integer numEnvio = 1;

    /** "A" administrativos, "D" docentes, "T" todos. */
    @Builder.Default
    private String salida = "T";

    @Builder.Default
    private Integer dependenciaId = 0;

    /** Mayor a cero: el universo sale de las novedades del código. */
    @Builder.Default
    private Integer codigoId = 0;

    @Builder.Default
    private Boolean cci = false;

    @Builder.Default
    private Boolean individual = false;

    @Builder.Default
    private List<LegajoAcreditar> legajos = new ArrayList<>();
}
