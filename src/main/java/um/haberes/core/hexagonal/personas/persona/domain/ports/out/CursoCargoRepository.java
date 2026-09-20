package um.haberes.core.hexagonal.personas.persona.domain.ports.out;

import java.util.List;

public interface CursoCargoRepository {

    List<Long> findLegajoIdsByAnhoAndMes(Integer anho, Integer mes);

    List<Long> findLegajoIdsByAnhoAndMesAndDesarraigo(Integer anho, Integer mes, Byte desarraigo);

    List<Long> findLegajoIdsByCursoIds(List<Long> cursoIds);
}
