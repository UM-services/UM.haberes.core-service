package um.haberes.core.hexagonal.liquidaciones.codigo.domain.model;

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
public class Codigo {

    private Integer codigoId;

    @Builder.Default
    private String nombre = "";

    @Builder.Default
    private Byte docente = 0;

    @Builder.Default
    private Byte noDocente = 0;

    @Builder.Default
    private Byte transferible = 0;

    @Builder.Default
    private Byte incluidoEtec = 0;

    private Long afipConceptoSueldoIdPrimerSemestre;

    private Long afipConceptoSueldoIdSegundoSemestre;
}
