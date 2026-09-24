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
public class CargoImputacionDto {
    private Integer categoriaId;
    private String categoriaNombre;
    private Integer dependenciaId;
    private String dependenciaAcronimo;
    private Integer facultadId;
    private String facultadNombre;
    private Integer geograficaId;
    private String geograficaNombre;
    private BigDecimal basico;
    private BigDecimal antiguedad;
    private BigDecimal cuentaSueldos;
}
