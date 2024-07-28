package um.haberes.core.service.extern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.client.CursoCargoContratadoClient;
import um.haberes.core.kotlin.model.extern.CursoCargoContratadoDto;

import java.util.List;

@Service
@Slf4j
public class CursoCargoContratadoService {

    private final CursoCargoContratadoClient cursoCargoContratadoClient;

    @Autowired
    public CursoCargoContratadoService(CursoCargoContratadoClient cursoCargoContratadoClient) {
        this.cursoCargoContratadoClient = cursoCargoContratadoClient;
    }

    public List<CursoCargoContratadoDto> findAllByCurso(Long cursoId, Integer anho, Integer mes) {
        var cursoCargos = cursoCargoContratadoClient.findAllByCurso(cursoId, anho, mes);
        try {
            log.debug("CursoCargos -> {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(cursoCargos));
        } catch (JsonProcessingException e) {
            log.debug("CursoCargos -> null");
        }
        return cursoCargos;
    }
}
