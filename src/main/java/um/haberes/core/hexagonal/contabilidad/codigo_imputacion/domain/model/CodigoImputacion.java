package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model;

import java.math.BigDecimal;

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
public class CodigoImputacion {

    private Long codigoImputacionId;

    private Integer dependenciaId;

    private Integer facultadId;

    private Integer geograficaId;

    private Integer codigoId;

    private BigDecimal cuentaSueldosDocente;

    private BigDecimal cuentaAportesDocente;

    private BigDecimal cuentaSueldosNoDocente;

    private BigDecimal cuentaAportesNoDocente;

    public String key() {
        return dependenciaId + "." + facultadId + "." + geograficaId + "." + codigoId;
    }
}
