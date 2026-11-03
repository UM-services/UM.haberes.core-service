package um.haberes.core.hexagonal.liquidaciones.item.infrastructure.web.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.dto.CodigoResponse;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.dto.PersonaResponse;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private Long itemId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private Integer codigoId;

    private String codigoNombre;

    private BigDecimal importe;

    private CodigoResponse codigo;

    private PersonaResponse persona;
}
