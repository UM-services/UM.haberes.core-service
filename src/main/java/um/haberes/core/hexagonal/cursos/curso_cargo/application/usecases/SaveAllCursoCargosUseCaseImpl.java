package um.haberes.core.hexagonal.cursos.curso_cargo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in.SaveAllCursoCargosUseCase;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.out.CursoCargoRepository;

@Component
@RequiredArgsConstructor
public class SaveAllCursoCargosUseCaseImpl implements SaveAllCursoCargosUseCase {

    private final CursoCargoRepository cursoCargoRepository;

    @Transactional
    @Override
    public List<CursoCargo> saveAllCursoCargos(List<CursoCargo> cursoCargos) {
        return cursoCargoRepository.saveAll(cursoCargos);
    }
}
