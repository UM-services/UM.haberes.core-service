package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.web.dto;

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
public class CodigoImputacionResponse {

    private Long codigoImputacionId;

    private Integer dependenciaId;

    private Integer facultadId;

    private Integer geograficaId;

    private Integer codigoId;

    private BigDecimal cuentaSueldosDocente;

    private BigDecimal cuentaAportesDocente;

    private BigDecimal cuentaSueldosNoDocente;

    private BigDecimal cuentaAportesNoDocente;
}
