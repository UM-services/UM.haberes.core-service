package um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;

public interface SaveAllCursoDesarraigosUseCase {

    List<CursoDesarraigo> saveAllCursoDesarraigos(List<CursoDesarraigo> cursoDesarraigos);
}
