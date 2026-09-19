package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.model;

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
public class CategoriaImputacion {

    private Long categoriaImputacionId;

    private Integer dependenciaId;

    private Integer facultadId;

    private Integer geograficaId;

    private Integer categoriaId;

    private BigDecimal cuentaSueldos;

    private BigDecimal cuentaAportes;

    public String key() {
        return dependenciaId + "." + facultadId + "." + geograficaId + "." + categoriaId;
    }
}
