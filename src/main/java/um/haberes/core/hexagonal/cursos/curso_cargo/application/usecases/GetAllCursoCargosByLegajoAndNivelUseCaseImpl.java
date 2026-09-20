package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByLegajoAndNivelUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class GetAllCursoCargosByLegajoAndNivelUseCaseImpl implements GetAllCursoCargosByLegajoAndNivelUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public List<CursoCargo> getAllCursoCargosByLegajoAndNivel(Long legajoId, Integer anho, Integer mes,
            Integer nivelId) {
        return cursoCargoRepository.findAllByLegajoIdAndAnhoAndMesAndCursoNivelId(legajoId, anho, mes, nivelId);
    }
}
