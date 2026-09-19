package um.haberes.core.hexagonal.liquidaciones.cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.in.GetCargosDocentesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.out.CargoRepository;

@Component
@RequiredArgsConstructor
public class GetCargosDocentesByLegajoUseCaseImpl implements GetCargosDocentesByLegajoUseCase {

    private static final Byte DOCENTE = 1;

    private final CargoRepository cargoRepository;

    @Override
    public List<Cargo> getCargosDocentesByLegajo(Long legajoId, Integer anho, Integer mes) {
        return cargoRepository.findAllVigentesByLegajoIdAndAnhoAndMesAndCategoriaDocente(legajoId, anho, mes, DOCENTE);
    }
}
