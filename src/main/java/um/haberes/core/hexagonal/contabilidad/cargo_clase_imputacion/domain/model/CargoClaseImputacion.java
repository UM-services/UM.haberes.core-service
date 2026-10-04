package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model;

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
public class CargoClaseImputacion {

    private Long cargoClaseImputacionId;

    private Integer dependenciaId;

    private Integer facultadId;

    private Integer geograficaId;

    private Long cargoClaseId;

    private BigDecimal cuentaSueldos;

    private BigDecimal cuentaAportes;

    public String key() {
        return dependenciaId + "." + facultadId + "." + geograficaId + "." + cargoClaseId;
    }
}
