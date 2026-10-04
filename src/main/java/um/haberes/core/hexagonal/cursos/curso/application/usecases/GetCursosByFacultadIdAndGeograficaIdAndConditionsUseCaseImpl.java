package um.haberes.core.hexagonal.cursos.curso.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.GetCursosByFacultadIdAndGeograficaIdAndConditionsUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.out.CursoRepository;

@Component
@RequiredArgsConstructor
public class GetCursosByFacultadIdAndGeograficaIdAndConditionsUseCaseImpl
        implements GetCursosByFacultadIdAndGeograficaIdAndConditionsUseCase {

    private final CursoRepository cursoRepository;

    @Override
    public List<Curso> getCursosByFacultadIdAndGeograficaIdAndConditions(Integer facultadId, Integer geograficaId,
            List<String> conditions) {
        return cursoRepository.findAllByFacultadIdAndGeograficaIdAndConditions(facultadId, geograficaId, conditions);
    }
}
