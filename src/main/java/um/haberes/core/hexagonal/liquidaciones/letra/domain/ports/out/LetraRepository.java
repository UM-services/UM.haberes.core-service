package um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;

public interface LetraRepository {

    Letra save(Letra letra);

    List<Letra> saveAll(List<Letra> letras);

    Optional<Letra> findByLetraId(Long letraId);

    Optional<Letra> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);

    List<Letra> findAllByAnhoAndMes(Integer anho, Integer mes, Integer limit);

    void deleteAllByAnhoAndMes(Integer anho, Integer mes);

    void deleteAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);
}
