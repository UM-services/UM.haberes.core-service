package ar.edu.um.haberes.rest.extern.consumer;

import ar.edu.um.haberes.rest.configuration.HaberesConfiguration;
import ar.edu.um.haberes.rest.kotlin.model.extern.CuentaMovimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.time.OffsetDateTime;
import java.util.List;

@Component
public class CuentaMovimientoConsumer {

    @Autowired
    private HaberesConfiguration haberesConfiguration;

    public List<CuentaMovimiento> findAllByAsiento(OffsetDateTime fechaContable, Integer ordenContable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(haberesConfiguration.getTesiumServer());
        stringBuilder.append(":");
        stringBuilder.append(haberesConfiguration.getTesiumPort());
        WebClient webClient = WebClient.create();
        String url = MessageFormat.format("http://{0}/cuentaMovimiento/asiento/{1}/{2}", stringBuilder.toString(), fechaContable, ordenContable);
        Mono<List<CuentaMovimiento>> cuentaMovimientos = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(CuentaMovimiento.class)
                .collectList();
        return cuentaMovimientos.block();
    }
}
