package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.ModoLiquidacionEntity;

import java.util.Optional;

@Repository
public interface JpaModoLiquidacionRepository extends JpaRepository<ModoLiquidacionEntity, Integer> {

    public Optional<ModoLiquidacionEntity> findByModoLiquidacionId(Integer modoLiquidacionId);

}
