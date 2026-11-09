package um.haberes.core.hexagonal.liquidaciones.individual.application.service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.FichaLiquidacionIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.NovedadRegistradaIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.RegistroNovedadIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResumenGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in.EliminarLiquidacionIndividualUseCase;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in.EliminarNovedadIndividualUseCase;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in.GenerarCargosIndividualUseCase;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in.LiquidarIndividualUseCase;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in.ObtenerFichaLiquidacionIndividualUseCase;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.ports.in.RegistrarNovedadIndividualUseCase;

/** Fachada de aplicación del slice de liquidación individual. */
@Component
@RequiredArgsConstructor
public class LiquidacionIndividualService {

    private final ObtenerFichaLiquidacionIndividualUseCase obtenerFichaUseCase;

    private final GenerarCargosIndividualUseCase generarCargosUseCase;

    private final LiquidarIndividualUseCase liquidarUseCase;

    private final EliminarLiquidacionIndividualUseCase eliminarLiquidacionUseCase;

    private final RegistrarNovedadIndividualUseCase registrarNovedadUseCase;

    private final EliminarNovedadIndividualUseCase eliminarNovedadUseCase;

    public Optional<FichaLiquidacionIndividual> obtenerFicha(Long legajoId, Integer anho, Integer mes) {
        return obtenerFichaUseCase.obtener(legajoId, anho, mes);
    }

    public ResumenGeneracionCargos generarCargos(Long legajoId, Integer anho, Integer mes) {
        return generarCargosUseCase.generar(legajoId, anho, mes);
    }

    public ResultadoProcesoIndividual liquidar(Long legajoId, Integer anho, Integer mes, boolean forzar) {
        return liquidarUseCase.liquidar(legajoId, anho, mes, forzar);
    }

    public ResultadoProcesoIndividual eliminarLiquidacion(Long legajoId, Integer anho, Integer mes,
                                                          boolean forzar) {
        return eliminarLiquidacionUseCase.eliminar(legajoId, anho, mes, forzar);
    }

    public NovedadRegistradaIndividual registrarNovedad(Long legajoId, Integer anho, Integer mes,
                                                        RegistroNovedadIndividual registro) {
        return registrarNovedadUseCase.registrar(legajoId, anho, mes, registro);
    }

    public ResultadoProcesoIndividual eliminarNovedad(Long legajoId, Integer anho, Integer mes, Long novedadId,
                                                      boolean forzar) {
        return eliminarNovedadUseCase.eliminar(legajoId, anho, mes, novedadId, forzar);
    }
}
