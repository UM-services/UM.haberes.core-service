package um.haberes.core.hexagonal.cursos.curso_desarraigo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.GetCursoDesarraigoByUniqueUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.out.CursoDesarraigoRepository;

@Component
@RequiredArgsConstructor
public class GetCursoDesarraigoByUniqueUseCaseImpl implements GetCursoDesarraigoByUniqueUseCase {

    private final CursoDesarraigoRepository cursoDesarraigoRepository;

    @Override
    public Optional<CursoDesarraigo> getCursoDesarraigoByUnique(Long legajoId, Integer anho, Integer mes,
            Long cursoId) {
        return cursoDesarraigoRepository.findByLegajoIdAndAnhoAndMesAndCursoId(legajoId, anho, mes, cursoId);
    }
}
