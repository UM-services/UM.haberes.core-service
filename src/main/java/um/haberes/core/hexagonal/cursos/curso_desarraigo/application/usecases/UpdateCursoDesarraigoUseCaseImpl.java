package um.haberes.core.hexagonal.cursos.curso_desarraigo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.UpdateCursoDesarraigoUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.out.CursoDesarraigoRepository;

@Component
@RequiredArgsConstructor
public class UpdateCursoDesarraigoUseCaseImpl implements UpdateCursoDesarraigoUseCase {

    private final CursoDesarraigoRepository cursoDesarraigoRepository;

    @Override
    public Optional<CursoDesarraigo> updateCursoDesarraigo(Long cursoDesarraigoId, CursoDesarraigo cursoDesarraigo) {
        return cursoDesarraigoRepository.findByCursoDesarraigoId(cursoDesarraigoId)
                .map(existing -> cursoDesarraigoRepository.save(withId(cursoDesarraigoId, cursoDesarraigo)));
    }

    private CursoDesarraigo withId(Long cursoDesarraigoId, CursoDesarraigo cursoDesarraigo) {
        CursoDesarraigo.CursoDesarraigoBuilder builder = CursoDesarraigo.builder()
                .cursoDesarraigoId(cursoDesarraigoId)
                .legajoId(cursoDesarraigo.getLegajoId())
                .anho(cursoDesarraigo.getAnho())
                .mes(cursoDesarraigo.getMes())
                .cursoId(cursoDesarraigo.getCursoId())
                .geograficaId(cursoDesarraigo.getGeograficaId())
                .version(cursoDesarraigo.getVersion());
        if (cursoDesarraigo.getImporte() != null) {
            builder.importe(cursoDesarraigo.getImporte());
        }
        return builder.build();
    }
}
