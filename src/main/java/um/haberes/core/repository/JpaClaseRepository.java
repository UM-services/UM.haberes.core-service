package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.model.ClaseEntity;

import java.util.Optional;

public interface JpaClaseRepository extends JpaRepository<ClaseEntity, Integer> {

    Optional<ClaseEntity> findTopByOrderByClaseIdDesc();
    Optional<ClaseEntity> findByClaseId(Integer claseId);

}