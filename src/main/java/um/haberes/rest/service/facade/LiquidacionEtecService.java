package um.haberes.rest.service.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.rest.exception.InasistenciaDescuentoException;
import um.haberes.rest.service.InasistenciaDescuentoService;
import um.haberes.rest.service.LegajoInasistenciaService;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
public class LiquidacionEtecService {

    private final LegajoInasistenciaService legajoInasistenciaService;

    private final InasistenciaDescuentoService inasistenciaDescuentoService;

    @Autowired
    public LiquidacionEtecService(LegajoInasistenciaService legajoInasistenciaService, InasistenciaDescuentoService inasistenciaDescuentoService) {
        this.legajoInasistenciaService = legajoInasistenciaService;
        this.inasistenciaDescuentoService = inasistenciaDescuentoService;
    }

    public BigDecimal calcularPorcentajeAntiguedad(Long legajoId, Integer anho, Integer mes, Integer facultadId, Integer geograficaId) {
        BigDecimal porcentajeAntiguedad = new BigDecimal("0.2");
        Integer inasistencias = legajoInasistenciaService.getInasistenciasPorPeriodo(legajoId, anho, mes, facultadId, geograficaId);

        if (inasistencias > 0) {
            try {
                var inasistenciaDescuento = inasistenciaDescuentoService.findByInasistencias(facultadId, geograficaId, inasistencias);
                try {
                    log.debug("InasistenciaDescuento: {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(inasistenciaDescuento));
                } catch (JsonProcessingException e) {
                    log.debug("InasistenciaDescuento: null");
                }
                var porcentaje = BigDecimal.ONE.subtract(new BigDecimal(inasistenciaDescuento.getPorcentaje()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
                porcentajeAntiguedad = porcentajeAntiguedad.multiply(porcentaje).setScale(2, RoundingMode.HALF_UP);
            } catch (InasistenciaDescuentoException e) {
                log.debug("Sin Configuración de InasistenciaDescuento -> {}", e.getMessage());
            }
        }
        log.debug("Porcentaje Antiguedad -> {}", porcentajeAntiguedad);
        return porcentajeAntiguedad;
    }
}
