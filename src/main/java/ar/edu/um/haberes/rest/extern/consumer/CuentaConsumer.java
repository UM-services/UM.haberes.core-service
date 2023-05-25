package ar.edu.um.haberes.rest.extern.consumer;

import ar.edu.um.haberes.rest.configuration.HaberesConfiguration;
import ar.edu.um.haberes.rest.kotlin.model.extern.Cuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.List;

@Component
public class CuentaConsumer {

    @Autowired
    private HaberesConfiguration haberesConfiguration;

    public List<Cuenta> findAll() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(haberesConfiguration.getTesiumServer());
        stringBuilder.append(":");
        stringBuilder.append(haberesConfiguration.getTesiumPort());
        WebClient webClient = WebClient.create();
        String url = MessageFormat.format("http://{0}/cuenta/", stringBuilder.toString());
        Mono<List<Cuenta>> cuentas = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Cuenta.class)
                .collectList();
        return cuentas.block();
    }

}
