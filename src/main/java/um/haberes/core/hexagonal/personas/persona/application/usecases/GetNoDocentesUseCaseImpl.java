package um.haberes.core.hexagonal.personas.persona.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetNoDocentesUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class GetNoDocentesUseCaseImpl implements GetNoDocentesUseCase {

    private static final int SIN_LIMITE = 0;

    private final PersonaRepository personaRepository;

    private final CategoriaRepository categoriaRepository;

    private final LiquidacionRepository liquidacionRepository;

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    @Override
    public List<Persona> getNoDocentes(Integer anho, Integer mes) {
        List<Integer> categoriaIds = categoriaRepository.findAllByNoDocente((byte) 1).stream()
                .map(Categoria::getCategoriaId)
                .collect(Collectors.toList());
        List<Long> legajoIdsLiquidados = liquidacionRepository.findAllByAnhoAndMes(anho, mes, SIN_LIMITE).stream()
                .map(Liquidacion::getLegajoId)
                .collect(Collectors.toList());
        if (categoriaIds.isEmpty() || legajoIdsLiquidados.isEmpty()) {
            return List.of();
        }
        List<Long> legajoIds = cargoLiquidacionRepository
                .findAllByLegajoIdInAndCategoriaIdInAndAnhoAndMes(legajoIdsLiquidados, categoriaIds, anho, mes).stream()
                .map(CargoLiquidacion::getLegajoId)
                .collect(Collectors.toList());
        if (legajoIds.isEmpty()) {
            return List.of();
        }
        return personaRepository.findAllByLegajoIdsOrderedByLegajoId(legajoIds);
    }
}
