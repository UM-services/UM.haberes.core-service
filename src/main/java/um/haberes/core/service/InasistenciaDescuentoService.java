package um.haberes.core.service;

import org.springframework.stereotype.Service;
import um.haberes.core.exception.InasistenciaDescuentoException;
import um.haberes.core.model.InasistenciaDescuentoEntity;
import um.haberes.core.repository.JpaInasistenciaDescuentoRepository;

@Service
public class InasistenciaDescuentoService {

    private final JpaInasistenciaDescuentoRepository repository;

    public InasistenciaDescuentoService(JpaInasistenciaDescuentoRepository repository) {
        this.repository = repository;
    }


    public InasistenciaDescuentoEntity findByInasistencias(Integer facultadId, Integer geograficaId, Integer inasistencias) {
        InasistenciaDescuentoEntity inasistenciaDescuento = repository.findByFacultadIdAndGeograficaIdAndDesdeGreaterThanEqualAndHastaLessThanEqual(facultadId, geograficaId, inasistencias, inasistencias).orElseThrow(() -> new InasistenciaDescuentoException(facultadId, geograficaId, inasistencias));
        return inasistenciaDescuento;
    }

}
