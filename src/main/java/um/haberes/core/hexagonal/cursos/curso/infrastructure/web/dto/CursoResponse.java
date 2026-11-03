package um.haberes.core.hexagonal.cursos.curso.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.facultad.infrastructure.web.dto.FacultadResponse;
import um.haberes.core.hexagonal.geografica.infrastructure.web.dto.GeograficaResponse;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoResponse {

    private Long cursoId;

    private String nombre;

    private Integer facultadId;

    private Integer geograficaId;

    private Byte anual;

    private Byte semestre1;

    private Byte semestre2;

    private Integer nivelId;

    private Byte adicionalCargaHoraria;

    private FacultadResponse facultad;

    private GeograficaResponse geografica;
}
