package um.haberes.core.hexagonal.cursos.curso_desarraigo.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.CreateCursoDesarraigoUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.out.CursoDesarraigoRepository;

@Component
@RequiredArgsConstructor
public class CreateCursoDesarraigoUseCaseImpl implements CreateCursoDesarraigoUseCase {

    private final CursoDesarraigoRepository cursoDesarraigoRepository;

    @Override
    public CursoDesarraigo createCursoDesarraigo(CursoDesarraigo cursoDesarraigo) {
        return cursoDesarraigoRepository.save(cursoDesarraigo);
    }
}
