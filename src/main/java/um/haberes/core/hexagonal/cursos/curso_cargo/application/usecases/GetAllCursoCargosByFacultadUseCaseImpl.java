package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByFacultadUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class GetAllCursoCargosByFacultadUseCaseImpl implements GetAllCursoCargosByFacultadUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public List<CursoCargo> getAllCursoCargosByFacultad(Long legajoId, Integer anho, Integer mes,
            Integer facultadId) {
        return cursoCargoRepository.findAllByLegajoIdAndAnhoAndMesAndCursoFacultadId(legajoId, anho, mes, facultadId);
    }
}
