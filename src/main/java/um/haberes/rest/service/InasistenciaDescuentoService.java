package um.haberes.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.rest.exception.InasistenciaDescuentoException;
import um.haberes.rest.kotlin.model.InasistenciaDescuento;
import um.haberes.rest.kotlin.repository.InasistenciaDescuentoRepository;

@Service
public class InasistenciaDescuentoService {

    private final InasistenciaDescuentoRepository repository;

    @Autowired
    public InasistenciaDescuentoService(InasistenciaDescuentoRepository repository) {
        this.repository = repository;
    }


    public InasistenciaDescuento findByInasistencias(Integer facultadId, Integer geograficaId, Integer inasistencias) {
        InasistenciaDescuento inasistenciaDescuento = repository.findByFacultadIdAndGeograficaIdAndDesdeGreaterThanEqualAndHastaLessThanEqual(facultadId, geograficaId, inasistencias, inasistencias).orElseThrow(() -> new InasistenciaDescuentoException(facultadId, geograficaId, inasistencias));
        return inasistenciaDescuento;
    }

}
