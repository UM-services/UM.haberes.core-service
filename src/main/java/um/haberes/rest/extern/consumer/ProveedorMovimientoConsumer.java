package um.haberes.rest.extern.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import um.haberes.rest.configuration.HaberesConfiguration;
import um.haberes.rest.exception.extern.ProveedorMovimientoException;
import um.haberes.rest.kotlin.model.extern.ProveedorMovimiento;

@Component
@Slf4j
public class ProveedorMovimientoConsumer {

    @Autowired
    private HaberesConfiguration haberesConfiguration;

    private String getUrl() {
        return "http://" + haberesConfiguration.getTesiumServer() + ":" + haberesConfiguration.getTesiumPort() + "/proveedorMovimiento";
    }

    public ProveedorMovimiento findByOrdenPago(Integer prefijo, Long numeroComprobante) {
        WebClient webClient = WebClient.create();
        String url = this.getUrl() + "/ordenPago/" + prefijo + "/" + numeroComprobante;
        log.info("url={}", url);
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(ProveedorMovimiento.class)
                        .flatMap(error -> Mono.error(new ProveedorMovimientoException(prefijo, numeroComprobante))))
                .bodyToMono(ProveedorMovimiento.class)
                .block();
    }

    public ProveedorMovimiento findLastOrdenPago(Integer prefijo) {
        WebClient webClient = WebClient.create();
        String url = this.getUrl() + "/lastOrdenPago/" + prefijo;
        log.info("url={}", url);
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(ProveedorMovimiento.class)
                        .flatMap(error -> Mono.error(new ProveedorMovimientoException(prefijo))))
                .bodyToMono(ProveedorMovimiento.class)
                .block();
    }

}
