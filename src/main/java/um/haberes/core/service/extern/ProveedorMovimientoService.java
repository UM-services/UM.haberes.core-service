package um.haberes.core.service.extern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.client.ProveedorMovimientoClient;
import um.haberes.core.kotlin.model.extern.ProveedorMovimientoDto;

@Service
public class ProveedorMovimientoService {


    private final ProveedorMovimientoClient proveedorMovimientoClient;

    @Autowired
    public ProveedorMovimientoService(ProveedorMovimientoClient proveedorMovimientoClient) {
        this.proveedorMovimientoClient = proveedorMovimientoClient;
    }

    public ProveedorMovimientoDto findByOrdenPago(Integer prefijoPago, Long numeroComprobantePago) {
        return proveedorMovimientoClient.findByOrdenPago(prefijoPago, numeroComprobantePago);
    }

    public ProveedorMovimientoDto findLastOrdenPago(Integer prefijo) {
        return proveedorMovimientoClient.findLastOrdenPago(prefijo);
    }

}
