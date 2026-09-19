package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.exception.CargoLiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.CreateCargoLiquidacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.DeleteCargosByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.DeleteCargosDocentesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.DeleteCargosNoDocentesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.DeleteCargosNotInByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetAdicionalesHcsByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargoLiquidacionByIdUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargoNoDocenteByCategoriaUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargosActivosByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargosActivosByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargosByLegajoAndCategoriasAndPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargosByLegajoExcludingCategoriasAndPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargoLiquidacionesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargosByLegajosAndCategoriasAndPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargosDocentesByLegajoAndFacultadUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargoLiquidacionesDocentesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargosNoDocentesByLegajoAndFacultadUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargoLiquidacionesNoDocentesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargosNoDocentesHistoricosByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.SaveAllCargoLiquidacionesUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.UpdateCargoLiquidacionUseCase;

@Service
@RequiredArgsConstructor
public class CargoLiquidacionService {

    private final GetCargoLiquidacionesByLegajoUseCase getCargosByLegajoUseCase;
    private final GetCargoLiquidacionesDocentesByLegajoUseCase getCargosDocentesByLegajoUseCase;
    private final GetCargosDocentesByLegajoAndFacultadUseCase getCargosDocentesByLegajoAndFacultadUseCase;
    private final GetCargoLiquidacionesNoDocentesByLegajoUseCase getCargosNoDocentesByLegajoUseCase;
    private final GetCargosNoDocentesByLegajoAndFacultadUseCase getCargosNoDocentesByLegajoAndFacultadUseCase;
    private final GetAdicionalesHcsByLegajoUseCase getAdicionalesHcsByLegajoUseCase;
    private final GetCargosNoDocentesHistoricosByLegajoUseCase getCargosNoDocentesHistoricosByLegajoUseCase;
    private final GetCargosActivosByPeriodoUseCase getCargosActivosByPeriodoUseCase;
    private final GetCargosActivosByLegajoUseCase getCargosActivosByLegajoUseCase;
    private final GetCargosByLegajosAndCategoriasAndPeriodoUseCase getCargosByLegajosAndCategoriasAndPeriodoUseCase;
    private final GetCargosByLegajoAndCategoriasAndPeriodoUseCase getCargosByLegajoAndCategoriasAndPeriodoUseCase;
    private final GetCargosByLegajoExcludingCategoriasAndPeriodoUseCase getCargosByLegajoExcludingCategoriasAndPeriodoUseCase;
    private final GetCargoLiquidacionByIdUseCase getCargoLiquidacionByIdUseCase;
    private final GetCargoNoDocenteByCategoriaUseCase getCargoNoDocenteByCategoriaUseCase;
    private final CreateCargoLiquidacionUseCase createCargoLiquidacionUseCase;
    private final UpdateCargoLiquidacionUseCase updateCargoLiquidacionUseCase;
    private final SaveAllCargoLiquidacionesUseCase saveAllCargoLiquidacionesUseCase;
    private final DeleteCargosNotInByPeriodoUseCase deleteCargosNotInByPeriodoUseCase;
    private final DeleteCargosByPeriodoUseCase deleteCargosByPeriodoUseCase;
    private final DeleteCargosDocentesByLegajoUseCase deleteCargosDocentesByLegajoUseCase;
    private final DeleteCargosNoDocentesByLegajoUseCase deleteCargosNoDocentesByLegajoUseCase;

    public List<CargoLiquidacion> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        return getCargosByLegajoUseCase.getCargosByLegajo(legajoId, anho, mes);
    }

    public List<CargoLiquidacion> findAllDocenteByLegajo(Long legajoId, Integer anho, Integer mes) {
        return getCargosDocentesByLegajoUseCase.getCargosDocentesByLegajo(legajoId, anho, mes);
    }

    public List<CargoLiquidacion> findAllDocenteByLegajoAndFacultad(Long legajoId, Integer anho, Integer mes,
            Integer facultadId) {
        return getCargosDocentesByLegajoAndFacultadUseCase.getCargosDocentesByLegajoAndFacultad(legajoId, anho, mes,
                facultadId);
    }

    public List<CargoLiquidacion> findAllNoDocenteByLegajo(Long legajoId, Integer anho, Integer mes) {
        return getCargosNoDocentesByLegajoUseCase.getCargosNoDocentesByLegajo(legajoId, anho, mes);
    }

    public List<CargoLiquidacion> findAllNoDocenteByLegajoAndFacultad(Long legajoId, Integer anho, Integer mes,
            Integer facultadId) {
        return getCargosNoDocentesByLegajoAndFacultadUseCase.getCargosNoDocentesByLegajoAndFacultad(legajoId, anho,
                mes, facultadId);
    }

    public List<CargoLiquidacion> findAllAdicionalHCSByLegajo(Long legajoId, Integer anho, Integer mes) {
        return getAdicionalesHcsByLegajoUseCase.getAdicionalesHcsByLegajo(legajoId, anho, mes);
    }

    public List<CargoLiquidacion> findAllNoDocenteHistByLegajo(Long legajoId) {
        return getCargosNoDocentesHistoricosByLegajoUseCase.getCargosNoDocentesHistoricosByLegajo(legajoId);
    }

    public List<CargoLiquidacion> findAllActivosByPeriodo(Integer anho, Integer mes) {
        return getCargosActivosByPeriodoUseCase.getCargosActivosByPeriodo(anho, mes);
    }

    public List<CargoLiquidacion> findAllActivosByLegajo(Long legajoId, Integer anho, Integer mes) {
        return getCargosActivosByLegajoUseCase.getCargosActivosByLegajo(legajoId, anho, mes);
    }

    public List<CargoLiquidacion> findAllByLegajoIdInAndCategoriaIdInAndAnhoAndMes(List<Long> legajos,
            List<Integer> categorias, Integer anho, Integer mes) {
        return getCargosByLegajosAndCategoriasAndPeriodoUseCase.getCargosByLegajosAndCategoriasAndPeriodo(legajos,
                categorias, anho, mes);
    }

    public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdIn(Long legajoId, Integer anho,
            Integer mes, List<Integer> categorias) {
        return getCargosByLegajoAndCategoriasAndPeriodoUseCase.getCargosByLegajoAndCategoriasAndPeriodo(legajoId,
                anho, mes, categorias);
    }

    public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdNotIn(Long legajoId, Integer anho,
            Integer mes, List<Integer> categoriaIds) {
        return getCargosByLegajoExcludingCategoriasAndPeriodoUseCase.getCargosByLegajoExcludingCategoriasAndPeriodo(
                legajoId, anho, mes, categoriaIds);
    }

    public CargoLiquidacion findByCargoId(Long cargoLiquidacionId) {
        return getCargoLiquidacionByIdUseCase.getCargoLiquidacionById(cargoLiquidacionId)
                .orElseThrow(() -> new CargoLiquidacionException(cargoLiquidacionId));
    }

    public CargoLiquidacion findByCategoriaNoDocente(Long legajoId, Integer anho, Integer mes, Integer categoriaId) {
        return getCargoNoDocenteByCategoriaUseCase.getCargoNoDocenteByCategoria(legajoId, anho, mes, categoriaId)
                .orElseThrow(() -> new CargoLiquidacionException(legajoId, anho, mes, categoriaId));
    }

    public CargoLiquidacion add(CargoLiquidacion cargoLiquidacion) {
        return createCargoLiquidacionUseCase.createCargoLiquidacion(cargoLiquidacion);
    }

    public CargoLiquidacion update(CargoLiquidacion cargoLiquidacion, Long cargoLiquidacionId) {
        return updateCargoLiquidacionUseCase.updateCargoLiquidacion(cargoLiquidacionId, cargoLiquidacion)
                .orElseThrow(() -> new CargoLiquidacionException(cargoLiquidacionId));
    }

    public List<CargoLiquidacion> saveAll(List<CargoLiquidacion> cargos, Integer version, Boolean withVersion) {
        return saveAllCargoLiquidacionesUseCase.saveAllCargoLiquidaciones(cargos, version, withVersion);
    }

    public void deleteAllNotInByPeriodo(List<Long> legajos, Integer anho, Integer mes) {
        deleteCargosNotInByPeriodoUseCase.deleteCargosNotInByPeriodo(legajos, anho, mes);
    }

    public void deleteByPeriodo(Integer anho, Integer mes) {
        deleteCargosByPeriodoUseCase.deleteCargosByPeriodo(anho, mes);
    }

    public void deleteAllCargosDocentes(Long legajoId, Integer anho, Integer mes) {
        deleteCargosDocentesByLegajoUseCase.deleteCargosDocentesByLegajo(legajoId, anho, mes);
    }

    public void deleteAllCargosNoDocentes(Long legajoId, Integer anho, Integer mes) {
        deleteCargosNoDocentesByLegajoUseCase.deleteCargosNoDocentesByLegajo(legajoId, anho, mes);
    }
}
