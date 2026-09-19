package um.haberes.core.service.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.exception.view.TotalNovedadException;
import um.haberes.core.model.view.TotalNovedad;
import um.haberes.core.repository.view.JpaTotalNovedadRepository;

import java.util.List;

@Service
public class TotalNovedadService {

    @Autowired
    private JpaTotalNovedadRepository repository;

    public List<TotalNovedad> findAllByPeriodo(Integer anho, Integer mes) {
        return repository.findAllByAnhoAndMes(anho, mes);
    }

    public TotalNovedad findByUnique(Integer anho, Integer mes, Integer codigoId) {
        return repository.findByAnhoAndMesAndCodigoId(anho, mes, codigoId)
                .orElseThrow(() -> new TotalNovedadException(anho, mes, codigoId));
    }

}
