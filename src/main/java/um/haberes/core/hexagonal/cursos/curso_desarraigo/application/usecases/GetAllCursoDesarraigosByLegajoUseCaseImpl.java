package um.haberes.core.hexagonal.cursos.curso_desarraigo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.GetAllCursoDesarraigosByLegajoUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.out.CursoDesarraigoRepository;

@Component
@RequiredArgsConstructor
public class GetAllCursoDesarraigosByLegajoUseCaseImpl implements GetAllCursoDesarraigosByLegajoUseCase {

    private final CursoDesarraigoRepository cursoDesarraigoRepository;

    @Override
    public List<CursoDesarraigo> getAllCursoDesarraigosByLegajo(Long legajoId, Integer anho, Integer mes) {
        return cursoDesarraigoRepository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }
}
