package um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;

public interface CargoRepository {

    Cargo save(Cargo cargo);

    Optional<Cargo> findByCargoId(Long cargoId);

    List<Cargo> findAllByLegajoIdOrderByCargoIdDesc(Long legajoId);

    List<Cargo> findAllVigentesByLegajoIdAndAnhoAndMesAndCategoriaDocente(Long legajoId, Integer anho, Integer mes,
            Byte docente);

    List<Cargo> findAllVigentesByLegajoIdAndAnhoAndMesAndCategoriaNoDocente(Long legajoId, Integer anho, Integer mes,
            Byte noDocente);

    void deleteByCargoId(Long cargoId);
}
