package um.haberes.core.hexagonal.liquidaciones.novedad.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in.GetNovedadesByImportadoUseCase;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.out.NovedadRepository;

@Component
@RequiredArgsConstructor
public class GetNovedadesByImportadoUseCaseImpl implements GetNovedadesByImportadoUseCase {

    private final NovedadRepository novedadRepository;

    @Override
    public List<Novedad> getNovedadesByImportado(Byte importado, Integer anho, Integer mes) {
        return novedadRepository.findAllByImportadoAndAnhoAndMes(importado, anho, mes);
    }
}
