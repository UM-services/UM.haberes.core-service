/**
 *
 */
package um.haberes.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import um.haberes.core.exception.ControlException;
import um.haberes.core.model.ControlEntity;
import um.haberes.core.repository.JpaControlRepository;

/**
 * @author daniel
 */
@Service
public class ControlService {

    private final JpaControlRepository repository;

    @Autowired
    public ControlService(JpaControlRepository repository) {
        this.repository = repository;
    }

    @Cacheable("controles")
    public ControlEntity findByPeriodo(Integer anho, Integer mes) {
        return repository.findByAnhoAndMes(anho, mes).orElseThrow(() -> new ControlException(anho, mes));
    }

    @CacheEvict(value = "controles", allEntries = true)
    public ControlEntity add(ControlEntity control) {
        repository.save(control);
        return control;
    }

    @CacheEvict(value = "controles", allEntries = true)
    public ControlEntity update(ControlEntity newControl, Long controlId) {
        return repository.findByControlId(controlId).map(control -> {
            control = new ControlEntity(
                    controlId,
                    newControl.getAnho(),
                    newControl.getMes(),
                    newControl.getFechaDesde(),
                    newControl.getFechaHasta(),
                    newControl.getFechaPago(),
                    newControl.getAporteJubilatorio(),
                    newControl.getDepositoBanco(),
                    newControl.getFechaDeposito(),
                    newControl.getDoctorado(),
                    newControl.getMaestria(),
                    newControl.getEspecializacion(),
                    newControl.getFamiliaNumerosa(),
                    newControl.getEscuelaPrimaria(),
                    newControl.getEscuelaSecundaria(),
                    newControl.getEscuelaPrimariaNumerosa(),
                    newControl.getEscuelaSecundariaNumerosa(),
                    newControl.getPrenatal(),
                    newControl.getLibre(),
                    newControl.getAyudaEscolar(),
                    newControl.getMatrimonio(),
                    newControl.getNacimiento(),
                    newControl.getFuncionDireccion(),
                    newControl.getMayorResponsabilidadPatrimonial(),
                    newControl.getPolimedb(),
                    newControl.getPolimedo(),
                    newControl.getMontoeci(),
                    newControl.getValampo(),
                    newControl.getJubilaem(),
                    newControl.getInssjpem(),
                    newControl.getOsociaem(),
                    newControl.getJubilpat(),
                    newControl.getInssjpat(),
                    newControl.getOsocipat(),
                    newControl.getAnsalpat(),
                    newControl.getSalfapat(),
                    newControl.getMinimoAporte(),
                    newControl.getMaximoAporte(),
                    newControl.getMincontr(),
                    newControl.getMaximo1sijp(),
                    newControl.getMaximo2sijp(),
                    newControl.getMaximo3sijp(),
                    newControl.getMaximo4sijp(),
                    newControl.getMaximo5sijp(),
                    newControl.getEstadoDocenteTitular(),
                    newControl.getEstadoDocenteAdjunto(),
                    newControl.getEstadoDocenteAuxiliar(),
                    newControl.getAdicionalHoraCargoClase(),
                    newControl.getHoraReferenciaEtec(),
                    newControl.getModoLiquidacionId(),
                    null);
            repository.save(control);
            return control;
        }).orElseThrow(() -> new ControlException(controlId));
    }

}
