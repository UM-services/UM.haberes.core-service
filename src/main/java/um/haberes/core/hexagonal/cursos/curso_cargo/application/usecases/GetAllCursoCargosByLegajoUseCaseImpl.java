package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByLegajoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class GetAllCursoCargosByLegajoUseCaseImpl implements GetAllCursoCargosByLegajoUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public List<CursoCargo> getAllCursoCargosByLegajo(Long legajoId, Integer anho, Integer mes) {
        return cursoCargoRepository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }
}
