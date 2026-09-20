package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.persistence.entity.LegajoContabilidadEntity;

@Repository
public interface JpaLegajoContabilidadRepository extends JpaRepository<LegajoContabilidadEntity, Long> {

    List<LegajoContabilidadEntity> findAllByAnhoAndMesAndDiferencia(Integer anho, Integer mes, Byte diferencia);

    Optional<LegajoContabilidadEntity> findByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);
}
