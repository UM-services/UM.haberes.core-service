package um.haberes.rest.extern.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import um.haberes.rest.configuration.HaberesConfiguration;
import um.haberes.rest.kotlin.model.extern.CursoCargoContratado;

import java.util.List;

@Component
@Slf4j
public class CursoCargoContratadoConsumer {

    @Autowired
    private HaberesConfiguration haberesConfiguration;

    private String getUrl() {
        return "http://" + haberesConfiguration.getTesiumServer() + ":" + haberesConfiguration.getTesiumPort() + "/cursocargocontratado";
    }

    public List<CursoCargoContratado> findAllByCurso(Long cursoId, Integer anho, Integer mes) {
        WebClient webClient = WebClient.create();
        String url = this.getUrl() + "/curso/" + cursoId + "/" + anho + "/" + mes;
        Mono<List<CursoCargoContratado>> cursoCargoContratados = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(CursoCargoContratado.class)
                .collectList();
        return cursoCargoContratados.block();
    }
}
