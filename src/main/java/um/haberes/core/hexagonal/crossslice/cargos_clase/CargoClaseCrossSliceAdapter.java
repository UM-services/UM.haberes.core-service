package um.haberes.core.hexagonal.crossslice.cargos_clase;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.cargos_clase.model.CargoConClaseView;
import um.haberes.core.model.CargoClaseDetalleEntity;
import um.haberes.core.model.CargoClaseEntity;
import um.haberes.core.model.CargoClasePeriodoEntity;
import um.haberes.core.service.CargoClaseDetalleService;

/**
 * Adaptador cross-slice de cargos con clase.
 *
 * <p>{@code cargos_clase} todavía no es un slice hexagonal: el slice dueño es la entidad legacy
 * {@link CargoClaseDetalleEntity}. Esta vista evita serializarla y mantiene el dominio del
 * consumidor libre de tipos ajenos.
 */
@Component
@RequiredArgsConstructor
public class CargoClaseCrossSliceAdapter implements CargoClaseCrossSlice {

    private static final String SEPARADOR = " - ";

    private final CargoClaseDetalleService cargoClaseDetalleService;

    @Override
    public List<CargoConClaseView> porLegajo(Long legajoId, Integer anho, Integer mes) {
        return cargoClaseDetalleService.findAllByLegajo(legajoId, anho, mes).stream()
                .map(this::toView)
                .toList();
    }

    @Override
    public boolean tieneCargos(Long legajoId, Integer anho, Integer mes) {
        return !cargoClaseDetalleService.findAllByLegajo(legajoId, anho, mes).isEmpty();
    }

    private CargoConClaseView toView(CargoClaseDetalleEntity detalle) {
        CargoClasePeriodoEntity periodo = detalle.getCargoClasePeriodo();
        int horas = periodo != null ? periodo.getHoras() : detalle.getHoras();
        BigDecimal valorHora = periodo != null && periodo.getValorHora() != null
                ? periodo.getValorHora()
                : detalle.getValorHora();
        return new CargoConClaseView(detalle.getCargoClaseDetalleId(), detalle.getAnho(), detalle.getMes(),
                descripcion(detalle.getCargoClase()),
                detalle.getFacultad() == null ? null : detalle.getFacultad().getReducido(),
                detalle.getGeografica() == null ? null : detalle.getGeografica().getNombre(),
                periodo == null ? null : periodo.getPeriodoDesde(),
                periodo == null ? null : periodo.getPeriodoHasta(),
                horas, valorHora);
    }

    /** Misma composición que el combo legado: {@code cargo + " - " + clase}. */
    private String descripcion(CargoClaseEntity cargoClase) {
        if (cargoClase == null) {
            return "";
        }
        String nombre = cargoClase.getNombre() == null ? "" : cargoClase.getNombre();
        if (cargoClase.getClase() == null || cargoClase.getClase().getNombre() == null) {
            return nombre;
        }
        return nombre + SEPARADOR + cargoClase.getClase().getNombre();
    }
}
