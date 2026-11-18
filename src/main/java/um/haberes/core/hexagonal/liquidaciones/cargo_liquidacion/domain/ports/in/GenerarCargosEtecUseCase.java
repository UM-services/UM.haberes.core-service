package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ResultadoGeneracionCargos;

/**
 * Generación y previsualización de los cargos ETEC (categorías con puntos).
 *
 * <p>Es el contrato que consume {@code haberes-etec}: allí queda la validación de negocio y acá
 * el cálculo y la escritura. La previsualización no escribe ni borra nada.</p>
 */
public interface GenerarCargosEtecUseCase {

    ResultadoGeneracionCargos generarByLegajo(Long legajoId, Integer anho, Integer mes);

    ResultadoGeneracionCargos generarByPeriodo(Integer anho, Integer mes);

    List<CargoLiquidacion> previsualizarByLegajo(Long legajoId, Integer anho, Integer mes);

    List<CargoLiquidacion> previsualizarByPeriodo(Integer anho, Integer mes);
}
