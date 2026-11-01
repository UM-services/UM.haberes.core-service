package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.GetAllCursoCargosByCargoTipoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class GetAllCursoCargosByCargoTipoUseCaseImpl implements GetAllCursoCargosByCargoTipoUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public List<CursoCargo> getAllCursoCargosByCargoTipo(Long legajoId, Integer anho, Integer mes,
            Integer facultadId, Integer geograficaId, Byte anual, Byte semestre1, Byte semestre2,
            Integer cargoTipoId) {
        return cursoCargoRepository
                .findAllByLegajoIdAndAnhoAndMesAndCargoTipoIdAndCursoFacultadIdAndCursoGeograficaIdAndCursoAnualAndCursoSemestre1AndCursoSemestre2(
                        legajoId, anho, mes, cargoTipoId, facultadId, geograficaId, anual, semestre1, semestre2);
    }
}
