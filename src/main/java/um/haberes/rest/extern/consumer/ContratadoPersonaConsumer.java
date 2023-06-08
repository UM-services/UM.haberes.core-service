package um.haberes.rest.extern.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import um.haberes.rest.configuration.HaberesConfiguration;
import um.haberes.rest.kotlin.model.extern.ContratadoPersona;
import um.haberes.rest.kotlin.model.extern.Cuenta;

import java.util.List;

@Component
@Slf4j
public class ContratadoPersonaConsumer {

    @Autowired
    private HaberesConfiguration haberesConfiguration;

    private String getUrl() {
        return "http://" + haberesConfiguration.getTesiumServer() + ":" + haberesConfiguration.getTesiumPort() + "/contratadopersona";
    }

    public List<ContratadoPersona> findAll() {
        WebClient webClient = WebClient.create();
        String url = this.getUrl() + "/";
        Mono<List<ContratadoPersona>> contratadoPersonas = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(ContratadoPersona.class)
                .collectList();
        return contratadoPersonas.block();
    }

}
