package um.haberes.core.hexagonal.crossslice.antiguedad;

import um.haberes.core.hexagonal.crossslice.antiguedad.model.AntiguedadView;

/** Contrato de cross-slice para la antigüedad de un legajo y período. */
public interface AntiguedadCrossSlice {

    AntiguedadView porLegajo(Long legajoId, Integer anho, Integer mes);
}
