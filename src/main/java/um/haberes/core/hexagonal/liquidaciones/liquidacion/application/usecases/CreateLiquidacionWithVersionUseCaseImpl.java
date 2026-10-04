package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.LiquidacionVersion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.CreateLiquidacionWithVersionUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionVersionRepository;

@Component
@RequiredArgsConstructor
public class CreateLiquidacionWithVersionUseCaseImpl implements CreateLiquidacionWithVersionUseCase {

    private final LiquidacionRepository liquidacionRepository;
    private final LiquidacionVersionRepository liquidacionVersionRepository;

    @Transactional
    @Override
    public Liquidacion createLiquidacionWithVersion(Liquidacion liquidacion, Integer version) {
        Liquidacion saved = liquidacionRepository.save(liquidacion);
        liquidacionVersionRepository.save(toVersion(saved, version));
        return saved;
    }

    static LiquidacionVersion toVersion(Liquidacion liquidacion, Integer version) {
        return LiquidacionVersion.builder()
                .legajoId(liquidacion.getLegajoId())
                .anho(liquidacion.getAnho())
                .mes(liquidacion.getMes())
                .version(version)
                .fechaLiquidacion(liquidacion.getFechaLiquidacion())
                .fechaAcreditacion(liquidacion.getFechaAcreditacion())
                .dependenciaId(liquidacion.getDependenciaId())
                .salida(liquidacion.getSalida())
                .totalRemunerativo(liquidacion.getTotalRemunerativo())
                .totalNoRemunerativo(liquidacion.getTotalNoRemunerativo())
                .totalDeduccion(liquidacion.getTotalDeduccion())
                .totalNeto(liquidacion.getTotalNeto())
                .bloqueado(liquidacion.getBloqueado())
                .estado(liquidacion.getEstado())
                .liquida(liquidacion.getLiquida())
                .build();
    }
}
