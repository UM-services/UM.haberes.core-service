package um.haberes.core.repository.view;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.kotlin.model.view.DocenteDesignacion;

import java.util.List;

public interface IDocenteDesignacionRepository extends JpaRepository<DocenteDesignacion, String> {

    public List<DocenteDesignacion> findAllByAnhoAndMes(Integer anho, Integer mes);

    List<DocenteDesignacion> findAllByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes);
}
