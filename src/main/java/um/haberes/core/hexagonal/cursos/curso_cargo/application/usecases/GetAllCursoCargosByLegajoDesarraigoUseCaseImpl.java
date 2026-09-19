package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByLegajoDesarraigoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class GetAllCursoCargosByLegajoDesarraigoUseCaseImpl implements GetAllCursoCargosByLegajoDesarraigoUseCase {

    private static final Byte DESARRAIGO = 1;

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public List<CursoCargo> getAllCursoCargosByLegajoDesarraigo(Long legajoId, Integer anho, Integer mes) {
        return cursoCargoRepository.findAllByLegajoIdAndAnhoAndMesAndDesarraigo(legajoId, anho, mes, DESARRAIGO);
    }
}
