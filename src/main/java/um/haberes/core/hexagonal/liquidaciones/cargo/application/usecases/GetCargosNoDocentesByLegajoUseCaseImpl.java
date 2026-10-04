package um.haberes.core.hexagonal.liquidaciones.cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in.GetCargosNoDocentesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.out.CargoRepository;

@Component
@RequiredArgsConstructor
public class GetCargosNoDocentesByLegajoUseCaseImpl implements GetCargosNoDocentesByLegajoUseCase {

    private static final Byte NO_DOCENTE = 1;

    private final CargoRepository cargoRepository;

    @Override
    public List<Cargo> getCargosNoDocentesByLegajo(Long legajoId, Integer anho, Integer mes) {
        return cargoRepository.findAllVigentesByLegajoIdAndAnhoAndMesAndCategoriaNoDocente(legajoId, anho, mes,
                NO_DOCENTE);
    }
}
