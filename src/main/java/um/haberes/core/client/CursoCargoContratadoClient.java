package um.haberes.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.haberes.core.kotlin.model.extern.CursoCargoContratadoDto;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "tesoreria-core-service", contextId = "cursoCargoContratadoClient", path = "/cursocargocontratado")
public interface CursoCargoContratadoClient {

    @GetMapping("/cursos/contrato/{contratoId}/periodo/{anho}/{mes}/persona/{personaId}/{documentoId}")
    List<CursoCargoContratadoDto> findAllByContrato(@PathVariable Long contratoId,
                                                    @PathVariable Integer anho,
                                                    @PathVariable Integer mes,
                                                    @PathVariable BigDecimal personaId,
                                                    @PathVariable Integer documentoId
    );

    @GetMapping("/cursos/persona/{personaId}/{documentoId}/periodo/{anho}/{mes}")
    List<CursoCargoContratadoDto> findAllByPersona(@PathVariable BigDecimal personaId,
                                                   @PathVariable Integer documentoId,
                                                   @PathVariable Integer anho,
                                                   @PathVariable Integer mes);

    @GetMapping("/curso/{cursoId}/{anho}/{mes}")
    List<CursoCargoContratadoDto> findAllByCurso(@PathVariable Long cursoId,
                                                 @PathVariable Integer anho,
                                                 @PathVariable Integer mes);

    @GetMapping("/{cursocargocontratadoId}")
    CursoCargoContratadoDto findByCursoCargo(@PathVariable Long cursocargocontratadoId);

    @PostMapping("/")
    CursoCargoContratadoDto add(@RequestBody CursoCargoContratadoDto cursocargocontratado);

    @PutMapping("/{cursoCargoContratadoId}")
    CursoCargoContratadoDto update(@RequestBody CursoCargoContratadoDto cursoCargoContratado,
                                   @PathVariable Long cursoCargoContratadoId);

    @DeleteMapping("/{cursoCargoContratadoId}")
    Void delete(@PathVariable Long cursoCargoContratadoId);

}
