package um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.persistence.adapter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.ports.out.CargoRepository;
import um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.persistence.entity.CargoEntity;
import um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.persistence.mapper.CargoMapper;
import um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.persistence.repository.JpaCargoRepository;
import um.haberes.core.util.Periodo;

@Component
@RequiredArgsConstructor
public class JpaCargoRepositoryAdapter implements CargoRepository {

    private final JpaCargoRepository jpaCargoRepository;
    private final CargoMapper cargoMapper;

    @Override
    public Cargo save(Cargo cargo) {
        return cargoMapper.toDomain(jpaCargoRepository.save(cargoMapper.toEntity(cargo)));
    }

    @Override
    public Optional<Cargo> findByCargoId(Long cargoId) {
        return jpaCargoRepository.findByCargoId(cargoId).map(cargoMapper::toDomain);
    }

    @Override
    public List<Cargo> findAllByLegajoIdOrderByCargoIdDesc(Long legajoId) {
        return toDomainList(jpaCargoRepository.findAllByLegajoIdOrderByCargoIdDesc(legajoId));
    }

    @Override
    public List<Cargo> findAllVigentesByLegajoIdAndAnhoAndMesAndCategoriaDocente(Long legajoId, Integer anho,
            Integer mes, Byte docente) {
        OffsetDateTime firstDay = Periodo.firstDay(anho, mes);
        OffsetDateTime lastDay = Periodo.lastDay(anho, mes);
        return toDomainList(Stream.concat(
                jpaCargoRepository.findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaIsNullAndCategoriaDocente(
                        legajoId, firstDay, docente).stream(),
                jpaCargoRepository.findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaGreaterThanEqualAndCategoriaDocente(
                        legajoId, firstDay, lastDay, docente).stream())
                .collect(Collectors.toList()));
    }

    @Override
    public List<Cargo> findAllVigentesByLegajoIdAndAnhoAndMesAndCategoriaNoDocente(Long legajoId, Integer anho,
            Integer mes, Byte noDocente) {
        OffsetDateTime firstDay = Periodo.firstDay(anho, mes);
        OffsetDateTime lastDay = Periodo.lastDay(anho, mes);
        return toDomainList(Stream.concat(
                jpaCargoRepository.findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaIsNullAndCategoriaNoDocente(
                        legajoId, firstDay, noDocente).stream(),
                jpaCargoRepository.findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaGreaterThanEqualAndCategoriaNoDocente(
                        legajoId, firstDay, lastDay, noDocente).stream())
                .collect(Collectors.toList()));
    }

    @Override
    public void deleteByCargoId(Long cargoId) {
        jpaCargoRepository.deleteByCargoId(cargoId);
    }

    private List<Cargo> toDomainList(List<CargoEntity> entities) {
        return entities.stream()
                .map(cargoMapper::toDomain)
                .collect(Collectors.toList());
    }
}
