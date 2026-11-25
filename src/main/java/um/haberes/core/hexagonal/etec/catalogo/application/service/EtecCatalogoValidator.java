package um.haberes.core.hexagonal.etec.catalogo.application.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import um.haberes.core.hexagonal.etec.liquidacion.application.service.EtecLiquidacionCalculator;
import um.haberes.core.hexagonal.liquidaciones.codigo.application.service.CodigoService;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;

/**
 * Verifica al arrancar que el catálogo global tenga los códigos ETEC. Las migraciones por SQL
 * directo no pasan por la API y, por lo tanto, no disparan los {@code @CacheEvict}; si el
 * catálogo llega incompleto, el error queda visible en el log (o impide el arranque con
 * {@code haberes.etec.catalogo-estricto=true}).
 */
@Component
@Slf4j
public class EtecCatalogoValidator implements ApplicationRunner {

    private final CodigoService codigoService;

    private final boolean estricto;

    public EtecCatalogoValidator(CodigoService codigoService,
                                 @Value("${haberes.etec.catalogo-estricto:false}") boolean estricto) {
        this.codigoService = codigoService;
        this.estricto = estricto;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            Set<Integer> presentes = codigoService.findAll().stream()
                    .map(Codigo::getCodigoId)
                    .collect(Collectors.toSet());
            List<Integer> faltantes = EtecLiquidacionCalculator.CODIGOS_ETEC.stream()
                    .filter(codigoId -> !presentes.contains(codigoId))
                    .toList();
            if (faltantes.isEmpty()) {
                log.info("Catálogo ETEC completo: {}", EtecLiquidacionCalculator.CODIGOS_ETEC);
                return;
            }
            String mensaje = "Catálogo ETEC incompleto: faltan los códigos " + faltantes
                    + ". Aplicar sql/010_codigos_etec.sql, sql/016_codigo_arraigo_etec.sql y "
                    + "sql/020_codigo_descuento_presentismo_etec.sql.";
            if (estricto) {
                throw new IllegalStateException(mensaje);
            }
            log.error(mensaje);
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            String mensaje = "No se pudo validar el catálogo ETEC al arrancar: " + e.getMessage();
            if (estricto) {
                throw new IllegalStateException(mensaje, e);
            }
            log.error(mensaje, e);
        }
    }
}
