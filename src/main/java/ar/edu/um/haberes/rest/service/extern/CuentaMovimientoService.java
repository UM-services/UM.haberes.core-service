package ar.edu.um.haberes.rest.service.extern;

import ar.edu.um.haberes.rest.extern.consumer.CuentaMovimientoConsumer;
import ar.edu.um.haberes.rest.kotlin.model.extern.CuentaMovimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class CuentaMovimientoService {

    @Autowired
    private CuentaMovimientoConsumer consumer;

    public List<CuentaMovimiento> findAllByAsiento(OffsetDateTime fechaContable, Integer ordenContable) {
        return consumer.findAllByAsiento(fechaContable, ordenContable);
    }

}
