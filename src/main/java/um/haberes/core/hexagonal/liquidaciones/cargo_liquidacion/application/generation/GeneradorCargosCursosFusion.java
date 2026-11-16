package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.generation;

import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.exception.CargoLiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ContextoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.UniversoCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneracionCargosDatosPort;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.GeneradorCargosLiquidacion;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;

/**
 * Cursos designados cuando el período liquida <b>con fusión</b>
 * ({@code control.modoLiquidacionId = 1}). La condición de modo es la que decide si la fuente
 * participa, en lugar de un {@code if} dentro de un generador único.
 */
@Component
@Order(20)
@RequiredArgsConstructor
public class GeneradorCargosCursosFusion implements GeneradorCargosLiquidacion {

    private final GeneracionCargosDatosPort datos;

    @Override
    public UniversoCargos universo() {
        return UniversoCargos.DOCENTE;
    }

    @Override
    public boolean aplica(ContextoGeneracionCargos contexto) {
        return contexto.porLegajo() && contexto.conFusion();
    }

    @Override
    public List<CargoLiquidacion> cargos(ContextoGeneracionCargos contexto) {
        return datos.cursosFusion(contexto.legajoId(), contexto.anho(), contexto.mes()).stream()
                .filter(cursoFusion -> !GeneradorCargosEtecHoras.FACULTAD_ETEC
                        .equals(cursoFusion.getFacultadId()))
                .map(cursoFusion -> CargoLiquidacionFactory.deCurso(contexto,
                        dependencia(contexto, cursoFusion.getFacultadId(), cursoFusion.getGeograficaId()),
                        cursoFusion.getCategoria()))
                .toList();
    }

    private Dependencia dependencia(ContextoGeneracionCargos contexto, Integer facultadId,
                                    Integer geograficaId) {
        Dependencia dependencia = contexto.dependenciasPorSede().get(facultadId + "." + geograficaId);
        if (dependencia == null) {
            throw new CargoLiquidacionException("No existe la dependencia de la facultad " + facultadId
                    + " y geográfica " + geograficaId);
        }
        return dependencia;
    }
}
