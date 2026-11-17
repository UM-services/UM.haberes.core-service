package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model;

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
public class PreparacionAcreditacion {

    private Integer anho;

    private Integer mes;

    @Builder.Default
    private List<FilaAcreditacion> filas = new ArrayList<>();

    @Builder.Default
    private List<Long> sinCbu = new ArrayList<>();

    @Builder.Default
    private List<Long> sinLiquidacion = new ArrayList<>();

    public BigDecimal getTotalAcreditado() {
        return filas.stream()
                .map(FilaAcreditacion::getAcreditado)
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
