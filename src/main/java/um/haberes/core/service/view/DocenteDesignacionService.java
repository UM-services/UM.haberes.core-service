package um.haberes.core.service.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.kotlin.model.view.DocenteDesignacion;
import um.haberes.core.repository.view.DocenteDesignacionRepository;

import java.util.List;

@Service
public class DocenteDesignacionService {

    @Autowired
    private DocenteDesignacionRepository repository;

    public List<DocenteDesignacion> findAllByPeriodo(Integer anho, Integer mes) {
        return repository.findAllByAnhoAndMes(anho, mes);
    }

    public List<DocenteDesignacion> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }
}
