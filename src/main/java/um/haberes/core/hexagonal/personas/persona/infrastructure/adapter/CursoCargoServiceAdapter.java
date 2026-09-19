package um.haberes.core.hexagonal.personas.persona.infrastructure.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.CursoCargoRepository;
import um.haberes.core.hexagonal.cursos.curso_cargo.application.service.CursoCargoService;

@Component
@RequiredArgsConstructor
public class CursoCargoServiceAdapter implements CursoCargoRepository {

    private final CursoCargoService cursoCargoService;

    @Override
    public List<Long> findLegajoIdsByAnhoAndMes(Integer anho, Integer mes) {
        return cursoCargoService.findAllByAnhoAndMes(anho, mes).stream()
                .map(CursoCargo::getLegajoId)
                .toList();
    }

    @Override
    public List<Long> findLegajoIdsByAnhoAndMesAndDesarraigo(Integer anho, Integer mes, Byte desarraigo) {
        return cursoCargoService.findAllByAnhoAndMesAndDesarraigo(anho, mes, desarraigo).stream()
                .map(CursoCargo::getLegajoId)
                .toList();
    }

    @Override
    public List<Long> findLegajoIdsByCursoIds(List<Long> cursoIds) {
        return cursoCargoService.findAllByCursoIdIn(cursoIds).stream()
                .map(CursoCargo::getLegajoId)
                .toList();
    }
}
