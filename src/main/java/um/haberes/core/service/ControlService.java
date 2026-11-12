/**
 *
 */
package um.haberes.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import um.haberes.core.exception.ControlException;
import um.haberes.core.exception.ControlOrigenException;
import um.haberes.core.exception.ControlYaExisteException;
import um.haberes.core.model.ControlEntity;
import um.haberes.core.repository.JpaControlRepository;
import um.haberes.core.util.Periodo;

import java.util.Optional;

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

    /**
     * Variante sin excepción de {@link #findByPeriodo}: el frontend la usa para
     * distinguir un período todavía vacío de un error real.
     */
    public Optional<ControlEntity> findByPeriodoOpcional(Integer anho, Integer mes) {
        return repository.findByAnhoAndMes(anho, mes);
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
                    newControl.getValorPuntoEtec(),
                    newControl.getModoLiquidacionId(),
                    null);
            repository.save(control);
            return control;
        }).orElseThrow(() -> new ControlException(controlId));
    }

    /**
     * Crea el período indicado copiando literalmente el registro del mes anterior
     * (incluidas sus fechas, igual que el sistema anterior). Falla si el período
     * destino ya tiene datos o si el anterior no existe.
     */
    @Transactional
    @CacheEvict(value = "controles", allEntries = true)
    public ControlEntity copiarPeriodo(Integer anho, Integer mes) {
        if (repository.findByAnhoAndMes(anho, mes).isPresent()) {
            throw new ControlYaExisteException(anho, mes);
        }
        Periodo anterior = Periodo.prevMonth(anho, mes);
        ControlEntity origen = repository.findByAnhoAndMes(anterior.getAnho(), anterior.getMes())
                .orElseThrow(() -> new ControlOrigenException(anterior.getAnho(), anterior.getMes()));
        return repository.save(copiaDe(origen, anho, mes));
    }

    private ControlEntity copiaDe(ControlEntity origen, Integer anho, Integer mes) {
        ControlEntity copia = new ControlEntity();
        copia.setAnho(anho);
        copia.setMes(mes);
        copia.setFechaDesde(origen.getFechaDesde());
        copia.setFechaHasta(origen.getFechaHasta());
        copia.setFechaPago(origen.getFechaPago());
        copia.setAporteJubilatorio(origen.getAporteJubilatorio());
        copia.setDepositoBanco(origen.getDepositoBanco());
        copia.setFechaDeposito(origen.getFechaDeposito());
        copia.setDoctorado(origen.getDoctorado());
        copia.setMaestria(origen.getMaestria());
        copia.setEspecializacion(origen.getEspecializacion());
        copia.setFamiliaNumerosa(origen.getFamiliaNumerosa());
        copia.setEscuelaPrimaria(origen.getEscuelaPrimaria());
        copia.setEscuelaSecundaria(origen.getEscuelaSecundaria());
        copia.setEscuelaPrimariaNumerosa(origen.getEscuelaPrimariaNumerosa());
        copia.setEscuelaSecundariaNumerosa(origen.getEscuelaSecundariaNumerosa());
        copia.setPrenatal(origen.getPrenatal());
        copia.setLibre(origen.getLibre());
        copia.setAyudaEscolar(origen.getAyudaEscolar());
        copia.setMatrimonio(origen.getMatrimonio());
        copia.setNacimiento(origen.getNacimiento());
        copia.setFuncionDireccion(origen.getFuncionDireccion());
        copia.setMayorResponsabilidadPatrimonial(origen.getMayorResponsabilidadPatrimonial());
        copia.setPolimedb(origen.getPolimedb());
        copia.setPolimedo(origen.getPolimedo());
        copia.setMontoeci(origen.getMontoeci());
        copia.setValampo(origen.getValampo());
        copia.setJubilaem(origen.getJubilaem());
        copia.setInssjpem(origen.getInssjpem());
        copia.setOsociaem(origen.getOsociaem());
        copia.setJubilpat(origen.getJubilpat());
        copia.setInssjpat(origen.getInssjpat());
        copia.setOsocipat(origen.getOsocipat());
        copia.setAnsalpat(origen.getAnsalpat());
        copia.setSalfapat(origen.getSalfapat());
        copia.setMinimoAporte(origen.getMinimoAporte());
        copia.setMaximoAporte(origen.getMaximoAporte());
        copia.setMincontr(origen.getMincontr());
        copia.setMaximo1sijp(origen.getMaximo1sijp());
        copia.setMaximo2sijp(origen.getMaximo2sijp());
        copia.setMaximo3sijp(origen.getMaximo3sijp());
        copia.setMaximo4sijp(origen.getMaximo4sijp());
        copia.setMaximo5sijp(origen.getMaximo5sijp());
        copia.setEstadoDocenteTitular(origen.getEstadoDocenteTitular());
        copia.setEstadoDocenteAdjunto(origen.getEstadoDocenteAdjunto());
        copia.setEstadoDocenteAuxiliar(origen.getEstadoDocenteAuxiliar());
        copia.setAdicionalHoraCargoClase(origen.getAdicionalHoraCargoClase());
        copia.setHoraReferenciaEtec(origen.getHoraReferenciaEtec());
        copia.setValorPuntoEtec(origen.getValorPuntoEtec());
        copia.setModoLiquidacionId(origen.getModoLiquidacionId());
        return copia;
    }

}
