package um.haberes.core.hexagonal.cursos.curso_desarraigo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.GetCursoDesarraigoByIdUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.out.CursoDesarraigoRepository;

@Component
@RequiredArgsConstructor
public class GetCursoDesarraigoByIdUseCaseImpl implements GetCursoDesarraigoByIdUseCase {

    private final CursoDesarraigoRepository cursoDesarraigoRepository;

    @Override
    public Optional<CursoDesarraigo> getCursoDesarraigoById(Long cursoDesarraigoId) {
        return cursoDesarraigoRepository.findByCursoDesarraigoId(cursoDesarraigoId);
    }
}
