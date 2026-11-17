package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
public class PreparacionAcreditacionResponse {

    private Integer anho;

    private Integer mes;

    private BigDecimal totalAcreditado;

    @Builder.Default
    private List<FilaAcreditacionResponse> filas = new ArrayList<>();

    @Builder.Default
    private List<Long> sinCbu = new ArrayList<>();

    @Builder.Default
    private List<Long> sinLiquidacion = new ArrayList<>();
}
