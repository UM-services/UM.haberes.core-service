package um.haberes.rest.service.extern;

import um.haberes.rest.extern.consumer.CuentaConsumer;
import um.haberes.rest.kotlin.model.extern.Cuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaService {

    @Autowired
    private CuentaConsumer consumer;

    public List<Cuenta> findAll() {
        return consumer.findAll();
    }

    public Cuenta findByNumeroCuenta(BigDecimal numeroCuenta) {
        return consumer.findByNumeroCuenta(numeroCuenta);
    }

    public Cuenta findByCuentaContableId(Long cuentaContableId) {
        return consumer.findByCuentaContableId(cuentaContableId);
    }
}
