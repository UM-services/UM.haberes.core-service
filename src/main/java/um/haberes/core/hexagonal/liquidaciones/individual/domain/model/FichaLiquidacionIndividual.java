package um.haberes.core.hexagonal.liquidaciones.individual.domain.model;

import java.util.List;

import um.haberes.core.hexagonal.crossslice.antiguedad.model.AntiguedadView;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.model.CargoLiquidacionView;
import um.haberes.core.hexagonal.crossslice.cargos_clase.model.CargoConClaseView;
import um.haberes.core.hexagonal.crossslice.control.model.ControlView;
import um.haberes.core.hexagonal.crossslice.item.model.ItemView;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;
import um.haberes.core.hexagonal.crossslice.persona.model.PersonaView;

/**
 * Ficha agregada de un legajo en un período.
 *
 * <p>Compone vistas de la capa cross-slice; no define ni duplica tipos de otros slices.
 */
public record FichaLiquidacionIndividual(Long legajoId, Integer anho, Integer mes, PersonaView persona,
                                         ControlView control, LiquidacionView liquidacion,
                                         AntiguedadView antiguedad,
                                         List<CargoLiquidacionView> cargosDocentes,
                                         List<CargoLiquidacionView> cargosNoDocentes,
                                         List<CargoConClaseView> cargosConClase,
                                         List<NovedadView> novedades, List<ItemView> items,
                                         AccionesLiquidacionIndividual acciones) {

    public FichaLiquidacionIndividual {
        cargosDocentes = cargosDocentes == null ? List.of() : List.copyOf(cargosDocentes);
        cargosNoDocentes = cargosNoDocentes == null ? List.of() : List.copyOf(cargosNoDocentes);
        cargosConClase = cargosConClase == null ? List.of() : List.copyOf(cargosConClase);
        novedades = novedades == null ? List.of() : List.copyOf(novedades);
        items = items == null ? List.of() : List.copyOf(items);
    }
}
