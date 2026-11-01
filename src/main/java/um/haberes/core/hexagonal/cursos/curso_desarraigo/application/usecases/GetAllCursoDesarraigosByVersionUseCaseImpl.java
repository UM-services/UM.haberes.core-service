package um.haberes.core.hexagonal.cursos.curso_desarraigo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.GetAllCursoDesarraigosByVersionUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.out.CursoDesarraigoRepository;

@Component
@RequiredArgsConstructor
public class GetAllCursoDesarraigosByVersionUseCaseImpl implements GetAllCursoDesarraigosByVersionUseCase {

    private final CursoDesarraigoRepository cursoDesarraigoRepository;

    @Override
    public List<CursoDesarraigo> getAllCursoDesarraigosByVersion(Long legajoId, Integer anho, Integer mes,
            Integer version) {
        return cursoDesarraigoRepository.findAllByLegajoIdAndAnhoAndMesAndVersion(legajoId, anho, mes, version);
    }
}
