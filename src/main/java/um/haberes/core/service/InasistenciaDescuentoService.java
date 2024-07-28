package um.haberes.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.exception.InasistenciaDescuentoException;
import um.haberes.core.kotlin.model.InasistenciaDescuento;
import um.haberes.core.kotlin.repository.InasistenciaDescuentoRepository;

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
