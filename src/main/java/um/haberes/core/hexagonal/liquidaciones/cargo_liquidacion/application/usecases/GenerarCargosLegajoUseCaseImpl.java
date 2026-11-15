package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.EnumSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service.GeneracionCargosOrquestador;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ResultadoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.UniversoCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GenerarCargosLegajoUseCase;

@Component
@RequiredArgsConstructor
public class GenerarCargosLegajoUseCaseImpl implements GenerarCargosLegajoUseCase {

    private final GeneracionCargosOrquestador orquestador;

    @Transactional
    @Override
    public ResultadoGeneracionCargos generar(Long legajoId, Integer anho, Integer mes) {
        return orquestador.generar(EnumSet.allOf(UniversoCargos.class), legajoId, anho, mes);
    }

    @Transactional
    @Override
    public ResultadoGeneracionCargos generar(Set<UniversoCargos> universos, Long legajoId, Integer anho,
                                             Integer mes) {
        return orquestador.generar(universos, legajoId, anho, mes);
    }
}
