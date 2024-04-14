package um.haberes.rest.service.extern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.rest.client.ContratadoPersonaClient;
import um.haberes.rest.kotlin.model.extern.ContratadoPersonaDto;

import java.util.List;

@Service
public class ContratadoPersonaService {

    private final ContratadoPersonaClient contratadoPersonaClient;

    @Autowired
    public ContratadoPersonaService(ContratadoPersonaClient contratadoPersonaClient) {
        this.contratadoPersonaClient = contratadoPersonaClient;
    }

    public List<ContratadoPersonaDto> findAll() {
        return contratadoPersonaClient.findAll();
    }

}
