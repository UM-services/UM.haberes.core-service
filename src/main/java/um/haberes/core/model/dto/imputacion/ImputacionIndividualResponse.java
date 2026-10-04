package um.haberes.core.model.dto.imputacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImputacionIndividualResponse {
    private Long legajoId;
    private Integer anho;
    private Integer mes;
    private List<CargoImputacionDto> cargos;
    private List<CargoClaseImputacionDto> cargosClase;
    private List<CodigoImputacionDetalleDto> codigos;
    private TotalesImputacionDto totales;
    private Byte diferencia;
}
