package um.haberes.core.hexagonal.etec.control_etec.application.usecases;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.control_etec.application.exception.ControlEtecException;
import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.in.SaveControlEtecUseCase;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.out.ControlEtecRepository;

@Component
@RequiredArgsConstructor
public class SaveControlEtecUseCaseImpl implements SaveControlEtecUseCase {

    private final ControlEtecRepository repository;

    @Transactional
    @Override
    public ControlEtec save(Integer anho, Integer mes, ControlEtec datos) {
        validarPeriodo(anho, mes);
        ControlEtec existente = repository.findByPeriodo(anho, mes).orElse(null);

        ControlEtec control = ControlEtec.builder()
                .controlEtecId(existente == null ? null : existente.getControlEtecId())
                .anho(anho)
                .mes(mes)
                .valorPuntoEtec(valor(datos.getValorPuntoEtec(), existente, ControlEtec::getValorPuntoEtec,
                        BigDecimal.ZERO))
                .montoFijoCargo(valor(datos.getMontoFijoCargo(), existente, ControlEtec::getMontoFijoCargo,
                        BigDecimal.ZERO))
                .porcentajePresentismo(valor(datos.getPorcentajePresentismo(), existente,
                        ControlEtec::getPorcentajePresentismo, ControlEtec.PORCENTAJE_PRESENTISMO_DEFAULT))
                .porcentajeAdicionalCarrera(valor(datos.getPorcentajeAdicionalCarrera(), existente,
                        ControlEtec::getPorcentajeAdicionalCarrera, ControlEtec.PORCENTAJE_ADICIONAL_CARRERA_DEFAULT))
                .garantia(valor(datos.getGarantia(), existente, ControlEtec::getGarantia, BigDecimal.ZERO))
                .horasAdicionalCargaHoraria(valor(datos.getHorasAdicionalCargaHoraria(), existente,
                        ControlEtec::getHorasAdicionalCargaHoraria, BigDecimal.ZERO))
                .valorHoraAdicionalCargaHoraria(valor(datos.getValorHoraAdicionalCargaHoraria(), existente,
                        ControlEtec::getValorHoraAdicionalCargaHoraria, BigDecimal.ZERO))
                .build();

        validarNoNegativo(control);
        return repository.save(control);
    }

    private BigDecimal valor(BigDecimal nuevo, ControlEtec existente,
                             java.util.function.Function<ControlEtec, BigDecimal> anterior,
                             BigDecimal defecto) {
        if (nuevo != null) {
            return nuevo;
        }
        if (existente != null) {
            BigDecimal previo = anterior.apply(existente);
            if (previo != null) {
                return previo;
            }
        }
        return defecto;
    }

    private void validarPeriodo(Integer anho, Integer mes) {
        if (anho == null || mes == null) {
            throw new ControlEtecException("El año y el mes son obligatorios");
        }
        if (mes < 1 || mes > 12) {
            throw new ControlEtecException("El mes debe estar entre 1 y 12");
        }
    }

    private void validarNoNegativo(ControlEtec control) {
        if (negativo(control.getValorPuntoEtec())) {
            throw new ControlEtecException("El valor del punto ETEC no puede ser negativo");
        }
        if (negativo(control.getMontoFijoCargo())) {
            throw new ControlEtecException("El monto fijo del cargo no puede ser negativo");
        }
        if (negativo(control.getPorcentajePresentismo())) {
            throw new ControlEtecException("El porcentaje de presentismo no puede ser negativo");
        }
        if (negativo(control.getPorcentajeAdicionalCarrera())) {
            throw new ControlEtecException("El porcentaje de adicional carrera no puede ser negativo");
        }
        if (negativo(control.getGarantia())) {
            throw new ControlEtecException("La garantía no puede ser negativa");
        }
        if (negativo(control.getHorasAdicionalCargaHoraria())) {
            throw new ControlEtecException("Las horas del adicional de carga horaria no pueden ser negativas");
        }
        if (negativo(control.getValorHoraAdicionalCargaHoraria())) {
            throw new ControlEtecException("El valor de la hora adicional no puede ser negativo");
        }
    }

    private boolean negativo(BigDecimal valor) {
        return valor != null && valor.compareTo(BigDecimal.ZERO) < 0;
    }
}
