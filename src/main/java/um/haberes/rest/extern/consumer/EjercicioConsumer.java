package um.haberes.rest.extern.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import um.haberes.rest.configuration.HaberesConfiguration;
import um.haberes.rest.exception.extern.EjercicioException;
import um.haberes.rest.kotlin.model.extern.Ejercicio;

import java.text.MessageFormat;
import java.time.OffsetDateTime;

@Component
@Slf4j
public class EjercicioConsumer {

    @Autowired
    private HaberesConfiguration haberesConfiguration;

    public Ejercicio findByFecha(OffsetDateTime lastDate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(haberesConfiguration.getTesiumServer());
        stringBuilder.append(":");
        stringBuilder.append(haberesConfiguration.getTesiumPort());
        WebClient webClient = WebClient.create();
        String url = MessageFormat.format("http://{0}/ejercicio/fecha/{1}", stringBuilder.toString(), lastDate);
        log.info("url={}", url);
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(Ejercicio.class)
                        .flatMap(error -> Mono.error(new EjercicioException(lastDate))))
                .bodyToMono(Ejercicio.class)
                .block();
    }

}
