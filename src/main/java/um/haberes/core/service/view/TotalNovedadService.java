package um.haberes.core.service.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.exception.view.TotalNovedadNotFoundException;
import um.haberes.core.kotlin.model.view.TotalNovedad;
import um.haberes.core.repository.view.TotalNovedadRepository;

import java.util.List;

@Service
public class TotalNovedadService {

    @Autowired
    private TotalNovedadRepository repository;

    public List<TotalNovedad> findAllByPeriodo(Integer anho, Integer mes) {
        return repository.findAllByAnhoAndMes(anho, mes);
    }

    public TotalNovedad findByUnique(Integer anho, Integer mes, Integer codigoId) {
        return repository.findByAnhoAndMesAndCodigoId(anho, mes, codigoId)
                .orElseThrow(() -> new TotalNovedadNotFoundException(anho, mes, codigoId));
    }

}
