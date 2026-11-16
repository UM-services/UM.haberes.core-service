package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.generation;

import java.util.List;
import java.util.Objects;

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
 * Cursos designados cuando el período liquida <b>sin fusión</b>
 * ({@code control.modoLiquidacionId = 2}).
 */
@Component
@Order(30)
@RequiredArgsConstructor
public class GeneradorCargosCursosSinFusion implements GeneradorCargosLiquidacion {

    private final GeneracionCargosDatosPort datos;

    @Override
    public UniversoCargos universo() {
        return UniversoCargos.DOCENTE;
    }

    @Override
    public boolean aplica(ContextoGeneracionCargos contexto) {
        return contexto.porLegajo() && contexto.sinFusion();
    }

    @Override
    public List<CargoLiquidacion> cargos(ContextoGeneracionCargos contexto) {
        return datos.cursosCargo(contexto.legajoId(), contexto.anho(), contexto.mes()).stream()
                .filter(cursoCargo -> cursoCargo.getCategoriaId() != null)
                .filter(cursoCargo -> !GeneradorCargosEtecHoras.FACULTAD_ETEC
                        .equals(Objects.requireNonNull(cursoCargo.getCurso()).getFacultadId()))
                .map(cursoCargo -> CargoLiquidacionFactory.deCurso(contexto,
                        dependencia(contexto, Objects.requireNonNull(cursoCargo.getCurso())),
                        cursoCargo.getCategoria()))
                .toList();
    }

    private Dependencia dependencia(ContextoGeneracionCargos contexto,
                                    um.haberes.core.hexagonal.cursos.curso.domain.model.Curso curso) {
        Dependencia dependencia = contexto.dependenciasPorSede()
                .get(curso.getFacultadId() + "." + curso.getGeograficaId());
        if (dependencia == null) {
            throw new CargoLiquidacionException("No existe la dependencia del curso "
                    + curso.getFacultadId() + "." + curso.getGeograficaId());
        }
        return dependencia;
    }
}
