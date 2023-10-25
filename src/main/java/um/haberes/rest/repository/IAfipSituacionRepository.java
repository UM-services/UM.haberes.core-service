package um.haberes.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.rest.kotlin.model.AfipSituacion;

import java.util.Optional;

@Repository
public interface IAfipSituacionRepository extends JpaRepository<AfipSituacion, Integer> {
    public Optional<AfipSituacion> findByAfipSituacionId(Integer afipSituacionId);

}
