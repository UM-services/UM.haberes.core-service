package um.haberes.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import um.haberes.core.kotlin.model.extern.ContratadoPersonaDto;

import java.util.List;

@FeignClient(name = "tesoreria-core-service", contextId = "contratadoPersonaClient", path = "/contratadopersona")
public interface ContratadoPersonaClient {

    @GetMapping("/")
    List<ContratadoPersonaDto> findAll();

}
