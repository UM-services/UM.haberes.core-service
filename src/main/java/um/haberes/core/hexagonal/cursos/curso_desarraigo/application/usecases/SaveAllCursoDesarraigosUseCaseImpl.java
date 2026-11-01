package um.haberes.core.hexagonal.cursos.curso_desarraigo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in.SaveAllCursoDesarraigosUseCase;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.out.CursoDesarraigoRepository;

@Component
@RequiredArgsConstructor
public class SaveAllCursoDesarraigosUseCaseImpl implements SaveAllCursoDesarraigosUseCase {

    private final CursoDesarraigoRepository cursoDesarraigoRepository;

    @Transactional
    @Override
    public List<CursoDesarraigo> saveAllCursoDesarraigos(List<CursoDesarraigo> cursoDesarraigos) {
        return cursoDesarraigoRepository.saveAll(cursoDesarraigos);
    }
}
