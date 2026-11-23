package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.generation;

import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ContextoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.UniversoCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneracionCargosDatosPort;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneradorCargosLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;

/**
 * Novedad de ajuste de básico (código 980). El importe del cargo es el importe de la novedad,
 * no el básico de la categoría.
 */
@Component
@Order(40)
@RequiredArgsConstructor
public class GeneradorCargosNovedadAjusteBasico implements GeneradorCargosLiquidacion {

    private static final int CODIGO_NOVEDAD_AJUSTE_BASICO = 980;

    private final GeneracionCargosDatosPort datos;

    @Override
    public UniversoCargos universo() {
        return UniversoCargos.DOCENTE;
    }

    @Override
    public boolean aplica(ContextoGeneracionCargos contexto) {
        return contexto.porLegajo();
    }

    @Override
    public List<CargoLiquidacion> cargos(ContextoGeneracionCargos contexto) {
        Categoria categoria = datos.categoria(CODIGO_NOVEDAD_AJUSTE_BASICO, contexto.anho(), contexto.mes());
        return datos.novedades(contexto.legajoId(), contexto.anho(), contexto.mes(),
                        CODIGO_NOVEDAD_AJUSTE_BASICO).stream()
                .map(novedad -> CargoLiquidacionFactory.deNovedadAjusteBasico(contexto, novedad, categoria))
                .toList();
    }
}
