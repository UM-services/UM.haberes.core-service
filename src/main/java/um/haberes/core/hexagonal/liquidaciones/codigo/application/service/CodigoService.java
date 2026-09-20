package um.haberes.core.hexagonal.liquidaciones.codigo.application.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import um.haberes.core.hexagonal.liquidaciones.codigo.application.exception.CodigoException;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.CodigoSearchResult;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.CreateCodigoUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.DeleteCodigoUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.FindCodigosBySearchUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.GetAllCodigosUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.GetCodigoByIdUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.GetCodigosByCodigoIdsUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.GetCodigosByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.GetCodigosByTransferibleUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.GetLastCodigoUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.SaveAllCodigosUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.UpdateCodigoUseCase;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodigoService {

    private final GetAllCodigosUseCase getAllCodigosUseCase;
    private final GetCodigosByCodigoIdsUseCase getCodigosByCodigoIdsUseCase;
    private final GetCodigosByPeriodoUseCase getCodigosByPeriodoUseCase;
    private final GetCodigosByTransferibleUseCase getCodigosByTransferibleUseCase;
    private final FindCodigosBySearchUseCase findCodigosBySearchUseCase;
    private final GetCodigoByIdUseCase getCodigoByIdUseCase;
    private final GetLastCodigoUseCase getLastCodigoUseCase;
    private final CreateCodigoUseCase createCodigoUseCase;
    private final UpdateCodigoUseCase updateCodigoUseCase;
    private final DeleteCodigoUseCase deleteCodigoUseCase;
    private final SaveAllCodigosUseCase saveAllCodigosUseCase;

    @Cacheable("codigos")
    public List<Codigo> findAll() {
        return getAllCodigosUseCase.getAllCodigos();
    }

    public List<Codigo> findAllByCodigoIds(List<Integer> codigoIds) {
        return getCodigosByCodigoIdsUseCase.getCodigosByCodigoIds(codigoIds);
    }

    public List<Codigo> findAllByPeriodo(Integer anho, Integer mes) {
        return getCodigosByPeriodoUseCase.getCodigosByPeriodo(anho, mes);
    }

    public List<Codigo> findAllByTransferible(Byte transferible) {
        return getCodigosByTransferibleUseCase.getCodigosByTransferible(transferible);
    }

    public List<CodigoSearchResult> findAllSearch(String chain) {
        return findCodigosBySearchUseCase.findCodigosBySearch(chain);
    }

    public Codigo findByCodigoId(Integer codigoId) {
        return getCodigoByIdUseCase.getCodigoById(codigoId)
                .orElseThrow(() -> new CodigoException(codigoId));
    }

    public Codigo findLast() {
        return getLastCodigoUseCase.getLastCodigo().orElseThrow(CodigoException::new);
    }

    @CacheEvict(value = "codigos", allEntries = true)
    public void delete(Integer codigoId) {
        deleteCodigoUseCase.deleteCodigo(codigoId);
    }

    @CacheEvict(value = "codigos", allEntries = true)
    public Codigo add(Codigo codigo) {
        Codigo saved = createCodigoUseCase.createCodigo(codigo);
        log.debug(saved.toString());
        return saved;
    }

    @CacheEvict(value = "codigos", allEntries = true)
    public Codigo update(Codigo newCodigo, Integer codigoId) {
        Codigo updated = updateCodigoUseCase.updateCodigo(codigoId, newCodigo)
                .orElseThrow(() -> new CodigoException(codigoId));
        log.debug(updated.toString());
        return updated;
    }

    @Transactional
    @CacheEvict(value = "codigos", allEntries = true)
    public List<Codigo> saveAll(List<Codigo> codigos) {
        List<Codigo> saved = saveAllCodigosUseCase.saveAllCodigos(codigos);
        log.debug(saved.toString());
        return saved;
    }
}
