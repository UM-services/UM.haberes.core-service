package um.haberes.core.service.extern;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import um.haberes.core.client.CursoCargoContratadoClient;
import um.haberes.core.kotlin.model.extern.CursoCargoContratadoDto;
import um.haberes.core.util.Jsonifier;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CursoCargoContratadoService {

    private final CursoCargoContratadoClient cursoCargoContratadoClient;

    public List<CursoCargoContratadoDto> findAllByCurso(Long cursoId, Integer anho, Integer mes) {
        var cursoCargos = cursoCargoContratadoClient.findAllByCurso(cursoId, anho, mes);
        log.debug("CursoCargo[] -> {}", Jsonifier.builder(cursoCargos).build());
        return cursoCargos;
    }
}
