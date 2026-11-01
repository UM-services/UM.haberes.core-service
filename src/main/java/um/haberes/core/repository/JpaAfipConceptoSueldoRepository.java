package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.AfipConceptoSueldoEntity;

import java.util.Optional;

@Repository
public interface JpaAfipConceptoSueldoRepository extends JpaRepository<AfipConceptoSueldoEntity, Long> {

    public Optional<AfipConceptoSueldoEntity> findByAfipConceptoSueldoId(Long afipConceptoSueldoId);

}
