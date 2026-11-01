package um.haberes.core.hexagonal.liquidaciones.codigo.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.UpdateCodigoUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out.CodigoRepository;

@Component
@RequiredArgsConstructor
public class UpdateCodigoUseCaseImpl implements UpdateCodigoUseCase {

    private final CodigoRepository codigoRepository;

    @Override
    public Optional<Codigo> updateCodigo(Integer codigoId, Codigo newCodigo) {
        return codigoRepository.findByCodigoId(codigoId).map(codigo -> {
            codigo.setNombre(newCodigo.getNombre());
            codigo.setDocente(newCodigo.getDocente());
            codigo.setNoDocente(newCodigo.getNoDocente());
            codigo.setTransferible(newCodigo.getTransferible());
            codigo.setIncluidoEtec(newCodigo.getIncluidoEtec());
            codigo.setAfipConceptoSueldoIdPrimerSemestre(newCodigo.getAfipConceptoSueldoIdPrimerSemestre());
            codigo.setAfipConceptoSueldoIdSegundoSemestre(newCodigo.getAfipConceptoSueldoIdSegundoSemestre());
            return codigoRepository.save(codigo);
        });
    }
}
