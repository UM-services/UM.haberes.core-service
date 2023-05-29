package um.haberes.rest.service.extern;

import um.haberes.rest.extern.consumer.EjercicioConsumer;
import um.haberes.rest.kotlin.model.extern.Ejercicio;
import um.haberes.rest.util.Periodo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class EjercicioService {

    @Autowired
    private EjercicioConsumer consumer;

    public Ejercicio findByPeriodo(Integer anho, Integer mes) {
        OffsetDateTime lastDate = Periodo.lastDay(anho, mes);
        return consumer.findByFecha(lastDate);
    }
}
