package um.haberes.rest.service.extern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.rest.extern.consumer.ProveedorMovimientoConsumer;
import um.haberes.rest.kotlin.model.extern.ProveedorMovimiento;

@Service
public class ProveedorMovimientoService {

    @Autowired
    private ProveedorMovimientoConsumer consumer;

    public ProveedorMovimiento findByOrdenPago(Integer prefijoPago, Long numeroComprobantePago) {
        return consumer.findByOrdenPago(prefijoPago, numeroComprobantePago);
    }

    public ProveedorMovimiento findLastOrdenPago(Integer prefijo) {
        return consumer.findLastOrdenPago(prefijo);
    }

}
