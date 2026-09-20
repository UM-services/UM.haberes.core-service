package um.haberes.core.hexagonal.liquidaciones.novedad.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.SaveAllNovedadesUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out.NovedadRepository;

@Component
@RequiredArgsConstructor
public class SaveAllNovedadesUseCaseImpl implements SaveAllNovedadesUseCase {

    private final NovedadRepository novedadRepository;

    @Transactional
    @Override
    public List<Novedad> saveAllNovedades(List<Novedad> novedades) {
        return novedadRepository.saveAll(novedades);
    }
}
