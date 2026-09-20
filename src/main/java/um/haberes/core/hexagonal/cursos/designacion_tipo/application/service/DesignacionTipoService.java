package um.haberes.core.hexagonal.cursos.designacion_tipo.application.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import um.haberes.core.hexagonal.cursos.designacion_tipo.application.exception.DesignacionTipoException;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model.DesignacionTipo;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.ports.in.FindDesignacionTipoByHorasSemanalesUseCase;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.ports.in.GetAllDesignacionTiposUseCase;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.ports.in.GetDesignacionTipoByIdUseCase;

@Service
@RequiredArgsConstructor
@Slf4j
public class DesignacionTipoService {

    private final GetAllDesignacionTiposUseCase getAllDesignacionTiposUseCase;
    private final GetDesignacionTipoByIdUseCase getDesignacionTipoByIdUseCase;
    private final FindDesignacionTipoByHorasSemanalesUseCase findDesignacionTipoByHorasSemanalesUseCase;

    public List<DesignacionTipo> findAll() {
        return getAllDesignacionTiposUseCase.getAllDesignacionTipos();
    }

    public DesignacionTipo findByHorasSemanales(BigDecimal horasSemanales) {
        return findDesignacionTipoByHorasSemanalesUseCase.findDesignacionTipoByHorasSemanales(horasSemanales)
                .orElseThrow(() -> new DesignacionTipoException(horasSemanales));
    }

    public DesignacionTipo findByDesignacionTipoId(Integer designacionTipoId) {
        DesignacionTipo designacionTipo = getDesignacionTipoByIdUseCase.getDesignacionTipoById(designacionTipoId)
                .orElseThrow(() -> new DesignacionTipoException(designacionTipoId));
        log.debug("DesignacionTipo: {}", designacionTipo);
        return designacionTipo;
    }
}
