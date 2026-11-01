package um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.dto;

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
public class CodigoRequest {

    @NotNull
    private Integer codigoId;

    private String nombre;

    private Byte docente;

    private Byte noDocente;

    private Byte transferible;

    private Byte incluidoEtec;

    private Long afipConceptoSueldoIdPrimerSemestre;

    private Long afipConceptoSueldoIdSegundoSemestre;
}
