package um.haberes.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import um.haberes.rest.kotlin.model.extern.ContratadoPersonaDto;

import java.util.List;

@FeignClient(name = "core-service/contratadopersona")
public interface ContratadoPersonaClient {

    @GetMapping("/")
    List<ContratadoPersonaDto> findAll();

}
