package um.haberes.core.hexagonal.crossslice.cargo_liquidacion;

import java.util.List;

import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.model.CargoLiquidacionView;

/** Contrato de cross-slice para los cargos liquidados de un legajo y período. */
public interface CargoLiquidacionCrossSlice {

    List<CargoLiquidacionView> porLegajo(Long legajoId, Integer anho, Integer mes);

    /** {@code true} si el legajo tiene al menos un cargo liquidado activo en el período. */
    boolean tieneCargosActivos(Long legajoId, Integer anho, Integer mes);
}
