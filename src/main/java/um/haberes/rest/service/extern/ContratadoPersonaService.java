package um.haberes.rest.service.extern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.rest.extern.consumer.ContratadoPersonaConsumer;
import um.haberes.rest.kotlin.model.extern.ContratadoPersona;

import java.util.List;

@Service
public class ContratadoPersonaService {

    @Autowired
    private ContratadoPersonaConsumer consumer;

    public List<ContratadoPersona> findAll() {
        return consumer.findAll();
    }
}
