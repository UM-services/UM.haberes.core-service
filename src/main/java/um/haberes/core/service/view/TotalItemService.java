package um.haberes.core.service.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.exception.view.TotalItemNotFoundException;
import um.haberes.core.exception.view.TotalNovedadNotFoundException;
import um.haberes.core.kotlin.model.view.TotalItem;
import um.haberes.core.kotlin.model.view.TotalNovedad;
import um.haberes.core.repository.view.ITotalItemRepository;

import java.util.List;

@Service
public class TotalItemService {

    @Autowired
    private ITotalItemRepository repository;

    public List<TotalItem> findAllByPeriodo(Integer anho, Integer mes) {
        return repository.findAllByAnhoAndMes(anho, mes);
    }

    public TotalItem findByUnique(Integer anho, Integer mes, Integer codigoId) {
        return repository.findByAnhoAndMesAndCodigoId(anho, mes, codigoId)
                .orElseThrow(() -> new TotalItemNotFoundException(anho, mes, codigoId));
    }
}
