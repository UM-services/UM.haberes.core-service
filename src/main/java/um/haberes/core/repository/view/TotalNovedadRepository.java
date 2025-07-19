package um.haberes.core.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.kotlin.model.view.TotalNovedad;

import java.util.List;
import java.util.Optional;

public interface TotalNovedadRepository extends JpaRepository<TotalNovedad, String> {

    public List<TotalNovedad> findAllByAnhoAndMes(Integer anho, Integer mes);

    public Optional<TotalNovedad> findByAnhoAndMesAndCodigoId(Integer anho, Integer mes, Integer codigoId);
}
