package um.haberes.core.hexagonal.liquidaciones.sijp.application.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.ArchivoSijp;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.VarianteSijp;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.ports.in.GenerarArchivoSijpUseCase;

@Service
@RequiredArgsConstructor
public class SijpService {

    private final GenerarArchivoSijpUseCase generarArchivoSijpUseCase;

    public ArchivoSijp generar(Integer anho, Integer mes, VarianteSijp variante) {
        return generarArchivoSijpUseCase.generar(anho, mes, variante);
    }

}
