package um.haberes.core.hexagonal.cursos.cargo_tipo.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.cargo_tipo.application.exception.CargoTipoException;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.model.CargoTipo;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.ports.in.GetAllCargoTiposByCargoTipoIdInUseCase;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.ports.in.GetAllCargoTiposUseCase;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.ports.in.GetCargoTipoByIdUseCase;

@Service
@RequiredArgsConstructor
public class CargoTipoService {

	private final GetAllCargoTiposUseCase getAllCargoTiposUseCase;
	private final GetAllCargoTiposByCargoTipoIdInUseCase getAllCargoTiposByCargoTipoIdInUseCase;
	private final GetCargoTipoByIdUseCase getCargoTipoByIdUseCase;

	public List<CargoTipo> findAll() {
		return getAllCargoTiposUseCase.getAllCargoTipos();
	}

	public List<CargoTipo> findAllByCargoTipoIdIn(List<Integer> cargoTipoIds) {
		return getAllCargoTiposByCargoTipoIdInUseCase.getAllCargoTiposByCargoTipoIdIn(cargoTipoIds);
	}

	public CargoTipo findByCargoTipoId(Integer cargoTipoId) {
		return getCargoTipoByIdUseCase.getCargoTipoById(cargoTipoId)
				.orElseThrow(() -> new CargoTipoException(cargoTipoId));
	}

}
