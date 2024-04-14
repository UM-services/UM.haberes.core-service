package um.haberes.rest.service.extern;

import lombok.extern.slf4j.Slf4j;
import um.haberes.rest.client.EjercicioClient;
import um.haberes.rest.kotlin.model.extern.EjercicioDto;
import um.haberes.rest.util.Periodo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@Slf4j
public class EjercicioService {

    private final EjercicioClient ejercicioClient;

    @Autowired
    public EjercicioService(EjercicioClient ejercicioClient) {
        this.ejercicioClient = ejercicioClient;
    }

    public EjercicioDto findByPeriodo(Integer anho, Integer mes) {
        OffsetDateTime lastDate = Periodo.lastDay(anho, mes);
        log.debug("LastDate -> {}", lastDate);
        return ejercicioClient.findByFecha(lastDate);
    }

    public EjercicioDto findByFecha(OffsetDateTime fecha) {
        return ejercicioClient.findByFecha(fecha);
    }
}
