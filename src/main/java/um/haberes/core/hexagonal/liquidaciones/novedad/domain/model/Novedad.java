package um.haberes.core.hexagonal.liquidaciones.novedad.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Novedad {

    private Long novedadId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private Integer codigoId;

    private Integer dependenciaId;

    @Builder.Default
    private BigDecimal importe = BigDecimal.ZERO;

    @Builder.Default
    private String value = "";

    private String observaciones;

    private Byte importado;

    private Long novedadUploadId;

    private Dependencia dependencia;

}
