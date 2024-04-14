package um.haberes.rest.service.extern;

import um.haberes.rest.client.CuentaClient;
import um.haberes.rest.kotlin.model.extern.CuentaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaService {

    private final CuentaClient cuentaClient;

    @Autowired
    public CuentaService(CuentaClient cuentaClient) {
        this.cuentaClient = cuentaClient;
    }

    public List<CuentaDto> findAll() {
        return cuentaClient.findAll();
    }

    public List<CuentaDto> findByStrings(List<String> conditions, Boolean visible) {
        return cuentaClient.findByStrings(conditions, visible);
    }

    public CuentaDto findByNumeroCuenta(BigDecimal numeroCuenta) {
        return cuentaClient.findByNumeroCuenta(numeroCuenta);
    }

    public CuentaDto findByCuentaContableId(Long cuentaContableId) {
        return cuentaClient.findByCuentaContableId(cuentaContableId);
    }

}
