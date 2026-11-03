package um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.liquidaciones.afip_concepto_sueldo.infrastructure.web.dto.AfipConceptoSueldoResponse;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodigoResponse {

    private Integer codigoId;

    private String nombre;

    private Byte docente;

    private Byte noDocente;

    private Byte transferible;

    private Byte incluidoEtec;

    private Long afipConceptoSueldoIdPrimerSemestre;

    private Long afipConceptoSueldoIdSegundoSemestre;

    private AfipConceptoSueldoResponse afipConceptoSueldoPrimerSemestre;

    private AfipConceptoSueldoResponse afipConceptoSueldoSegundoSemestre;
}
