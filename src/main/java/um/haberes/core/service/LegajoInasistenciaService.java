package um.haberes.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.model.LegajoInasistenciaEntity;
import um.haberes.core.repository.JpaLegajoInasistenciaRepository;

@Service
@Slf4j
public class LegajoInasistenciaService {

    private final JpaLegajoInasistenciaRepository repository;

    @Autowired
    public LegajoInasistenciaService(JpaLegajoInasistenciaRepository repository) {
        this.repository = repository;
    }

    public Integer getInasistenciasPorPeriodo(Long legajoId, Integer anho, Integer mes, Integer facultadId, Integer geograficaId) {
        LegajoInasistenciaEntity legajoInasistencia = repository.findByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(legajoId, anho, mes, facultadId, geograficaId).orElse(new LegajoInasistenciaEntity());
        try {
            log.debug("LegajoInasistenciaEntity -> {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(legajoInasistencia));
        } catch (JsonProcessingException e) {
            log.debug("LegajoInasistenciaEntity -> null");
        }
        return legajoInasistencia.getCantidadInasistencias();
    }

}
