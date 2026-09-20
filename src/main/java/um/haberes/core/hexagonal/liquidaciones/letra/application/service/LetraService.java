package um.haberes.core.hexagonal.liquidaciones.letra.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.letra.application.exception.LetraException;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in.CreateLetraUseCase;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in.DeleteLetraByUniqueKeyUseCase;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in.DeleteLetrasByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in.GetLetraByUniqueKeyUseCase;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in.GetLetrasByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in.SaveAllLetrasUseCase;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in.UpdateLetraUseCase;

@Service
@RequiredArgsConstructor
public class LetraService {

    private final GetLetraByUniqueKeyUseCase getLetraByUniqueKeyUseCase;
    private final GetLetrasByPeriodoUseCase getLetrasByPeriodoUseCase;
    private final CreateLetraUseCase createLetraUseCase;
    private final UpdateLetraUseCase updateLetraUseCase;
    private final SaveAllLetrasUseCase saveAllLetrasUseCase;
    private final DeleteLetrasByPeriodoUseCase deleteLetrasByPeriodoUseCase;
    private final DeleteLetraByUniqueKeyUseCase deleteLetraByUniqueKeyUseCase;

    public Letra findByUnique(Long legajoId, Integer anho, Integer mes) {
        return getLetraByUniqueKeyUseCase.getLetraByUniqueKey(legajoId, anho, mes)
                .orElseThrow(() -> new LetraException(legajoId, anho, mes));
    }

    public Letra add(Letra letra) {
        return createLetraUseCase.createLetra(letra);
    }

    public Letra update(Letra letra, Long letraId) {
        return updateLetraUseCase.updateLetra(letraId, letra)
                .orElseThrow(() -> new LetraException(letraId));
    }

    public List<Letra> findAllByPeriodo(Integer anho, Integer mes, Integer limit) {
        return getLetrasByPeriodoUseCase.getLetrasByPeriodo(anho, mes, limit);
    }

    public List<Letra> saveAllLetras(List<Letra> letras) {
        return saveAllLetrasUseCase.saveAllLetras(letras);
    }

    public void deleteByPeriodo(Integer anho, Integer mes) {
        deleteLetrasByPeriodoUseCase.deleteLetrasByPeriodo(anho, mes);
    }

    public void deleteByUnique(Long legajoId, Integer anho, Integer mes) {
        deleteLetraByUniqueKeyUseCase.deleteLetraByUniqueKey(legajoId, anho, mes);
    }
}
