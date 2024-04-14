package um.haberes.rest.service.extern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.rest.client.CursoCargoContratadoClient;
import um.haberes.rest.kotlin.model.extern.CursoCargoContratadoDto;

import java.util.List;

@Service
public class CursoCargoContratadoService {

    private final CursoCargoContratadoClient cursoCargoContratadoClient;

    @Autowired
    public CursoCargoContratadoService(CursoCargoContratadoClient cursoCargoContratadoClient) {
        this.cursoCargoContratadoClient = cursoCargoContratadoClient;
    }

    public List<CursoCargoContratadoDto> findAllByCurso(Long cursoId, Integer anho, Integer mes) {
        return cursoCargoContratadoClient.findAllByCurso(cursoId, anho, mes);
    }
}
