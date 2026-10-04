package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.LiquidacionVersion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.SaveAllLiquidacionesUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionVersionRepository;

@Component
@RequiredArgsConstructor
public class SaveAllLiquidacionesUseCaseImpl implements SaveAllLiquidacionesUseCase {

    private final LiquidacionRepository liquidacionRepository;
    private final LiquidacionVersionRepository liquidacionVersionRepository;

    @Transactional
    @Override
    public List<Liquidacion> saveAllLiquidaciones(List<Liquidacion> liquidaciones, Integer version) {
        List<Liquidacion> saved = liquidacionRepository.saveAll(liquidaciones);
        List<LiquidacionVersion> backups = saved.stream()
                .map(liquidacion -> CreateLiquidacionWithVersionUseCaseImpl.toVersion(liquidacion, version))
                .collect(Collectors.toList());
        liquidacionVersionRepository.saveAll(backups);
        return saved;
    }
}
