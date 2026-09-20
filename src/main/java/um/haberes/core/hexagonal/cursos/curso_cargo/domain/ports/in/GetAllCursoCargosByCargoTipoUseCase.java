package um.haberes.core.hexagonal.cursos.curso_cargo.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;

public interface GetAllCursoCargosByCargoTipoUseCase {

    List<CursoCargo> getAllCursoCargosByCargoTipo(Long legajoId, Integer anho, Integer mes, Integer facultadId,
            Integer geograficaId, Byte anual, Byte semestre1, Byte semestre2, Integer cargoTipoId);
}
