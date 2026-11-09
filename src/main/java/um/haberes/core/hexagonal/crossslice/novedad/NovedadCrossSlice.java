package um.haberes.core.hexagonal.crossslice.novedad;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;

/** Contrato de cross-slice para las novedades de un legajo y período. */
public interface NovedadCrossSlice {

    List<NovedadView> porLegajo(Long legajoId, Integer anho, Integer mes);

    Optional<NovedadView> porClaveSinDependencia(Long legajoId, Integer anho, Integer mes, Integer codigoId);

    NovedadView crear(Long legajoId, Integer anho, Integer mes, Integer codigoId, BigDecimal importe,
                      String value, String observaciones);

    NovedadView actualizar(Long novedadId, BigDecimal importe, String value, String observaciones);

    void eliminar(Long novedadId);
}
