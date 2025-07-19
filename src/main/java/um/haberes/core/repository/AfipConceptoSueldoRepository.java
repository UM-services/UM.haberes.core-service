package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.AfipConceptoSueldo;

import java.util.Optional;

@Repository
public interface AfipConceptoSueldoRepository extends JpaRepository<AfipConceptoSueldo, Long> {

    public Optional<AfipConceptoSueldo> findByAfipConceptoSueldoId(Long afipConceptoSueldoId);

}
