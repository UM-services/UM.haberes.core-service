package um.haberes.core.hexagonal.cursos.curso.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.in.UpdateCursoUseCase;
import um.haberes.core.hexagonal.cursos.curso.domain.ports.out.CursoRepository;

@Component
@RequiredArgsConstructor
public class UpdateCursoUseCaseImpl implements UpdateCursoUseCase {

    private final CursoRepository cursoRepository;

    @Override
    public Optional<Curso> updateCurso(Long cursoId, Curso curso) {
        return cursoRepository.findByCursoId(cursoId)
                .map(existing -> cursoRepository.save(withId(cursoId, curso)));
    }

    private Curso withId(Long cursoId, Curso curso) {
        Curso.CursoBuilder builder = Curso.builder()
                .cursoId(cursoId)
                .facultadId(curso.getFacultadId())
                .geograficaId(curso.getGeograficaId())
                .nivelId(curso.getNivelId());
        if (curso.getNombre() != null) {
            builder.nombre(curso.getNombre());
        }
        if (curso.getAnual() != null) {
            builder.anual(curso.getAnual());
        }
        if (curso.getSemestre1() != null) {
            builder.semestre1(curso.getSemestre1());
        }
        if (curso.getSemestre2() != null) {
            builder.semestre2(curso.getSemestre2());
        }
        if (curso.getAdicionalCargaHoraria() != null) {
            builder.adicionalCargaHoraria(curso.getAdicionalCargaHoraria());
        }
        return builder.build();
    }
}
