package um.haberes.core.model.dto.imputacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalesImputacionDto {
    private BigDecimal totalCargosBasico;
    private BigDecimal totalCargosAntiguedad;
    private BigDecimal totalClasesBasico;
    private BigDecimal totalClasesAntiguedad;
    private BigDecimal totalCodigosImporte;
    private BigDecimal totalBruto;
    private BigDecimal totalNoRemunerativo;
}
