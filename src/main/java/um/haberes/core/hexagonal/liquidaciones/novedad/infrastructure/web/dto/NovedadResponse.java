package um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.web.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.dto.CodigoResponse;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.dto.DependenciaResponse;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.dto.PersonaResponse;

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

    private PersonaResponse persona;

    private CodigoResponse codigo;

    private DependenciaResponse dependencia;
}
