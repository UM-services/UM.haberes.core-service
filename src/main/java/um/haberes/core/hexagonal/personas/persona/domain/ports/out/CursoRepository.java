package um.haberes.core.hexagonal.personas.persona.domain.ports.out;

import java.util.List;

public interface CursoRepository {

    List<Long> findCursoIdsByFacultadId(Integer facultadId);
}
