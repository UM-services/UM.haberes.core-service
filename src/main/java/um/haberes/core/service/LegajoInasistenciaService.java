package um.haberes.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.kotlin.model.LegajoInasistencia;
import um.haberes.core.repository.LegajoInasistenciaRepository;

@Service
@Slf4j
public class LegajoInasistenciaService {

    private final LegajoInasistenciaRepository repository;

    @Autowired
    public LegajoInasistenciaService(LegajoInasistenciaRepository repository) {
        this.repository = repository;
    }

    public Integer getInasistenciasPorPeriodo(Long legajoId, Integer anho, Integer mes, Integer facultadId, Integer geograficaId) {
        LegajoInasistencia legajoInasistencia = repository.findByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(legajoId, anho, mes, facultadId, geograficaId).orElse(new LegajoInasistencia());
        try {
            log.debug("LegajoInasistencia -> {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(legajoInasistencia));
        } catch (JsonProcessingException e) {
            log.debug("LegajoInasistencia -> null");
        }
        return legajoInasistencia.getCantidadInasistencias();
    }

}
