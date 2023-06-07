package um.haberes.rest.extern.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import um.haberes.rest.configuration.HaberesConfiguration;
import um.haberes.rest.exception.extern.CuentaException;
import um.haberes.rest.kotlin.model.extern.Cuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class CuentaConsumer {

    @Autowired
    private HaberesConfiguration haberesConfiguration;

    public List<Cuenta> findAll() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(haberesConfiguration.getTesiumServer());
        stringBuilder.append(":");
        stringBuilder.append(haberesConfiguration.getTesiumPort());
        WebClient webClient = WebClient.create();
        String url = "http://" + stringBuilder.toString() + "/cuenta/";
        Mono<List<Cuenta>> cuentas = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Cuenta.class)
                .collectList();
        return cuentas.block();
    }

    public Cuenta findByNumeroCuenta(BigDecimal numeroCuenta) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(haberesConfiguration.getTesiumServer());
        stringBuilder.append(":");
        stringBuilder.append(haberesConfiguration.getTesiumPort());
        WebClient webClient = WebClient.create();
        String url = "http://" + stringBuilder.toString() + "/cuenta/" + numeroCuenta;
        log.info("url={}", url);
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(Cuenta.class)
                        .flatMap(error -> Mono.error(new CuentaException(numeroCuenta))))
                .bodyToMono(Cuenta.class)
                .block();
    }

}
