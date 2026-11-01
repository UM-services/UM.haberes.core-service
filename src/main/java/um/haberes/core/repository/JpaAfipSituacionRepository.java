package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.AfipSituacionEntity;

import java.util.Optional;

@Repository
public interface JpaAfipSituacionRepository extends JpaRepository<AfipSituacionEntity, Integer> {
    public Optional<AfipSituacionEntity> findByAfipSituacionId(Integer afipSituacionId);

}
