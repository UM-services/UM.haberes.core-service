package um.haberes.rest.extern.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.BodyInserters;
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

    private String getUrl() {
        return "http://" + haberesConfiguration.getTesiumServer() + ":" + haberesConfiguration.getTesiumPort() + "/cuenta";
    }

    public List<Cuenta> findAll() {
        WebClient webClient = WebClient.create();
        String url = this.getUrl() + "/";
        Mono<List<Cuenta>> cuentas = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Cuenta.class)
                .collectList();
        return cuentas.block();
    }

    public List<Cuenta> findByStrings(List<String> conditions, Boolean visible) {
        WebClient webClient = WebClient.create();
        String url = this.getUrl() + "/search/" + visible;
        log.info("url={}", url);
        Mono<List<Cuenta>> cuentas = webClient.post()
                .uri(url)
                .body(BodyInserters.fromValue(conditions))
                .retrieve()
                .bodyToFlux(Cuenta.class)
                .collectList();
        return cuentas.block();
    }

    public Cuenta findByNumeroCuenta(BigDecimal numeroCuenta) {
        WebClient webClient = WebClient.create();
        String url = this.getUrl() + "/" + numeroCuenta;
        log.info("url={}", url);
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(Cuenta.class)
                        .flatMap(error -> Mono.error(new CuentaException(numeroCuenta))))
                .bodyToMono(Cuenta.class)
                .block();
    }

    public Cuenta findByCuentaContableId(Long cuentaContableId) {
        WebClient webClient = WebClient.create();
        String url = this.getUrl() + "/id/" + cuentaContableId;
        log.info("url={}", url);
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(Cuenta.class)
                        .flatMap(error -> Mono.error(new CuentaException(cuentaContableId))))
                .bodyToMono(Cuenta.class)
                .block();
    }

}
