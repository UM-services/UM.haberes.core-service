package ar.edu.um.haberes.rest.service.extern;

import ar.edu.um.haberes.rest.extern.consumer.CuentaConsumer;
import ar.edu.um.haberes.rest.kotlin.model.extern.Cuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService {

    @Autowired
    private CuentaConsumer consumer;

    public List<Cuenta> findAll() {
        return consumer.findAll();
    }
}
