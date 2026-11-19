package um.haberes.core.hexagonal.crossslice.cargos_clase;

import java.util.List;

import um.haberes.core.hexagonal.crossslice.cargos_clase.model.CargoConClaseView;

/** Contrato de cross-slice para los cargos con clase de un legajo y período. */
public interface CargoClaseCrossSlice {

    List<CargoConClaseView> porLegajo(Long legajoId, Integer anho, Integer mes);

    /** {@code true} si el legajo tiene al menos un cargo con clase en el período. */
    boolean tieneCargos(Long legajoId, Integer anho, Integer mes);
}
