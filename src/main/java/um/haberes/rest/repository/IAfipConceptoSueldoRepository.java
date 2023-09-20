package um.haberes.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.rest.kotlin.model.AfipConceptoSueldo;

import java.util.Optional;

@Repository
public interface IAfipConceptoSueldoRepository extends JpaRepository<AfipConceptoSueldo, Long> {

    public Optional<AfipConceptoSueldo> findByAfipConceptoSueldoId(Long afipConceptoSueldoId);

}
