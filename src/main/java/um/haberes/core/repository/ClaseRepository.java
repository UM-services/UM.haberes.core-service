package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.kotlin.model.Clase;

import java.util.Optional;

public interface ClaseRepository extends JpaRepository<Clase, Integer> {

    Optional<Clase> findTopByOrderByClaseIdDesc();
    Optional<Clase> findByClaseId(Integer claseId);

}