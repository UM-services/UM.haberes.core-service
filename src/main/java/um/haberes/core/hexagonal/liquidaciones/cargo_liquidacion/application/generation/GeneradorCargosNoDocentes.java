package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.generation;

import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service.CatalogoUniversosCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ContextoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.UniversoCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneracionCargosDatosPort;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneradorCargosLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;

/**
 * Cargos no docentes cargados en la tabla {@code cargo}.
 */
@Component
@Order(50)
@RequiredArgsConstructor
public class GeneradorCargosNoDocentes implements GeneradorCargosLiquidacion {

    private static final int CODIGO_NOVEDAD_AJUSTE_BASICO = 980;

    private final GeneracionCargosDatosPort datos;

    @Override
    public UniversoCargos universo() {
        return UniversoCargos.NO_DOCENTE;
    }

    @Override
    public boolean aplica(ContextoGeneracionCargos contexto) {
        return contexto.porLegajo();
    }

    @Override
    public List<CargoLiquidacion> cargos(ContextoGeneracionCargos contexto) {
        return datos.cargosNoDocentes(contexto.legajoId(), contexto.anho(), contexto.mes()).stream()
                .filter(cargo -> esCategoriaDeCargo(cargo.getCategoria()))
                .map(cargo -> CargoLiquidacionFactory.deCargo(contexto, cargo))
                .toList();
    }

    private boolean esCategoriaDeCargo(Categoria categoria) {
        return categoria != null
                && categoria.getCategoriaId() != null
                && categoria.getCategoriaId() != CODIGO_NOVEDAD_AJUSTE_BASICO
                && !CatalogoUniversosCargos.esCategoriaEtec(categoria);
    }
}
