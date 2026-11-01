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
public class CargoClaseImputacionDto {
    private Long cargoClaseId;
    private String cargoClaseNombre;
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
