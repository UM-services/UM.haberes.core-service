package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service.GeneracionCargosGuard;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service.GeneracionCargosOrquestador;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ResultadoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.UniversoCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GenerarCargosEtecUseCase;

@Component
@RequiredArgsConstructor
public class GenerarCargosEtecUseCaseImpl implements GenerarCargosEtecUseCase {

    private static final Set<UniversoCargos> UNIVERSO_ETEC = Set.of(UniversoCargos.ETEC);

    private final GeneracionCargosOrquestador orquestador;

    private final GeneracionCargosGuard guard;

    @Transactional
    @Override
    public ResultadoGeneracionCargos generarByLegajo(Long legajoId, Integer anho, Integer mes) {
        guard.validarLegajo(legajoId, anho, mes);
        return orquestador.generar(UNIVERSO_ETEC, legajoId, anho, mes);
    }

    @Transactional
    @Override
    public ResultadoGeneracionCargos generarByPeriodo(Integer anho, Integer mes) {
        guard.validarPeriodo(anho, mes);
        return orquestador.generar(UNIVERSO_ETEC, null, anho, mes);
    }

    @Override
    public List<CargoLiquidacion> previsualizarByLegajo(Long legajoId, Integer anho, Integer mes) {
        return orquestador.previsualizar(UNIVERSO_ETEC, legajoId, anho, mes).cargos();
    }

    @Override
    public List<CargoLiquidacion> previsualizarByPeriodo(Integer anho, Integer mes) {
        return orquestador.previsualizar(UNIVERSO_ETEC, null, anho, mes).cargos();
    }
}
