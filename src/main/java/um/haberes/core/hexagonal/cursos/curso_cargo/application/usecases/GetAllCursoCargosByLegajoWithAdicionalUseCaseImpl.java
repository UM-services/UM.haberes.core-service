package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByLegajoWithAdicionalUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class GetAllCursoCargosByLegajoWithAdicionalUseCaseImpl
        implements GetAllCursoCargosByLegajoWithAdicionalUseCase {

    private static final Byte ADICIONAL_CARGA_HORARIA = 1;

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public List<CursoCargo> getAllCursoCargosByLegajoWithAdicional(Long legajoId, Integer anho, Integer mes) {
        return cursoCargoRepository.findAllByLegajoIdAndAnhoAndMesAndCursoAdicionalCargaHoraria(legajoId, anho, mes,
                ADICIONAL_CARGA_HORARIA);
    }
}
