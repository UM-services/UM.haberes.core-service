package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.web.dto;

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
public class LegajoContabilidadResponse {

    private Long legajoContabilidadId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private Byte diferencia;

    private BigDecimal remunerativo;

    private BigDecimal noRemunerativo;
}
