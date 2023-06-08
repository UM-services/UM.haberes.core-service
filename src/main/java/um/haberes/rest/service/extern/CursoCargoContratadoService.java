package um.haberes.rest.service.extern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.rest.extern.consumer.CursoCargoContratadoConsumer;
import um.haberes.rest.kotlin.model.extern.CursoCargoContratado;

import java.util.List;

@Service
public class CursoCargoContratadoService {

    @Autowired
    private CursoCargoContratadoConsumer consumer;

    public List<CursoCargoContratado> findAllByCurso(Long cursoId, Integer anho, Integer mes) {
        return consumer.findAllByCurso(cursoId, anho, mes);
    }
}
