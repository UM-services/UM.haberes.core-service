package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByLegajoAndNivelIdsUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class GetAllCursoCargosByLegajoAndNivelIdsUseCaseImpl implements GetAllCursoCargosByLegajoAndNivelIdsUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public List<CursoCargo> getAllCursoCargosByLegajoAndNivelIds(Long legajoId, Integer anho, Integer mes,
            List<Integer> nivelIds) {
        return cursoCargoRepository.findAllByLegajoIdAndAnhoAndMesAndCursoNivelIdIn(legajoId, anho, mes, nivelIds);
    }
}
