package um.haberes.core.hexagonal.cursos.curso_desarraigo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.GetAllCursoDesarraigosUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.out.CursoDesarraigoRepository;

@Component
@RequiredArgsConstructor
public class GetAllCursoDesarraigosUseCaseImpl implements GetAllCursoDesarraigosUseCase {

    private final CursoDesarraigoRepository cursoDesarraigoRepository;

    @Override
    public List<CursoDesarraigo> getAllCursoDesarraigos() {
        return cursoDesarraigoRepository.findAll();
    }
}
