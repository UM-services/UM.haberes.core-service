package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.UpdateCursoCargoUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class UpdateCursoCargoUseCaseImpl implements UpdateCursoCargoUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public Optional<CursoCargo> updateCursoCargo(Long cursoCargoId, CursoCargo cursoCargo) {
        return cursoCargoRepository.findByCursoCargoId(cursoCargoId)
                .map(existing -> cursoCargoRepository.save(withId(cursoCargoId, cursoCargo)));
    }

    private CursoCargo withId(Long cursoCargoId, CursoCargo cursoCargo) {
        CursoCargo.CursoCargoBuilder builder = CursoCargo.builder()
                .cursoCargoId(cursoCargoId)
                .cursoId(cursoCargo.getCursoId())
                .anho(cursoCargo.getAnho())
                .mes(cursoCargo.getMes())
                .cargoTipoId(cursoCargo.getCargoTipoId())
                .legajoId(cursoCargo.getLegajoId())
                .designacionTipoId(cursoCargo.getDesignacionTipoId())
                .categoriaId(cursoCargo.getCategoriaId())
                .cursoCargoNovedadId(cursoCargo.getCursoCargoNovedadId());
        if (cursoCargo.getHorasSemanales() != null) {
            builder.horasSemanales(cursoCargo.getHorasSemanales());
        }
        if (cursoCargo.getHorasTotales() != null) {
            builder.horasTotales(cursoCargo.getHorasTotales());
        }
        if (cursoCargo.getDesarraigo() != null) {
            builder.desarraigo(cursoCargo.getDesarraigo());
        }
        return builder.build();
    }
}
