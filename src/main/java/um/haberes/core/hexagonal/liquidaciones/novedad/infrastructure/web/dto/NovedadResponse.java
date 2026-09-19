package um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.web.dto;

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
public class NovedadResponse {

    private Long novedadId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private Integer codigoId;

    private Integer dependenciaId;

    private BigDecimal importe;

    private String value;

    private String observaciones;

    private Byte importado;

    private Long novedadUploadId;
}
