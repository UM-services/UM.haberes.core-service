package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByPeriodoAndDesarraigoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class GetAllCursoCargosByPeriodoAndDesarraigoUseCaseImpl
        implements GetAllCursoCargosByPeriodoAndDesarraigoUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public List<CursoCargo> getAllCursoCargosByPeriodoAndDesarraigo(Integer anho, Integer mes, Byte desarraigo) {
        return cursoCargoRepository.findAllByAnhoAndMesAndDesarraigo(anho, mes, desarraigo);
    }
}
