package um.haberes.core.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.kotlin.model.view.TotalItem;

import java.util.List;
import java.util.Optional;

public interface TotalItemRepository extends JpaRepository<TotalItem, String> {
    public List<TotalItem> findAllByAnhoAndMes(Integer anho, Integer mes);

    public Optional<TotalItem> findByAnhoAndMesAndCodigoId(Integer anho, Integer mes, Integer codigoId);
}
