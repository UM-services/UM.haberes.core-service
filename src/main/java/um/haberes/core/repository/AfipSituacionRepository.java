package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.AfipSituacion;

import java.util.Optional;

@Repository
public interface AfipSituacionRepository extends JpaRepository<AfipSituacion, Integer> {
    public Optional<AfipSituacion> findByAfipSituacionId(Integer afipSituacionId);

}
