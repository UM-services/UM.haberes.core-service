package um.haberes.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.rest.kotlin.model.ModoLiquidacion;

import java.util.Optional;

@Repository
public interface IModoLiquidacionRepository extends JpaRepository<ModoLiquidacion, Integer> {

    public Optional<ModoLiquidacion> findByModoLiquidacionId(Integer modoLiquidacionId);

}
