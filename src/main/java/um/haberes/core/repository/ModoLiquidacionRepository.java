package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.ModoLiquidacion;

import java.util.Optional;

@Repository
public interface ModoLiquidacionRepository extends JpaRepository<ModoLiquidacion, Integer> {

    public Optional<ModoLiquidacion> findByModoLiquidacionId(Integer modoLiquidacionId);

}
