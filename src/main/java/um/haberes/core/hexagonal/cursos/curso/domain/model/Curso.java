package um.haberes.core.hexagonal.cursos.curso.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    private Long cursoId;

    @Builder.Default
    private String nombre = "";

    private Integer facultadId;

    private Integer geograficaId;

    @Builder.Default
    private Byte anual = 0;

    @Builder.Default
    private Byte semestre1 = 0;

    @Builder.Default
    private Byte semestre2 = 0;

    private Integer nivelId;

    @Builder.Default
    private Byte adicionalCargaHoraria = 0;

    private Facultad facultad;

    private Geografica geografica;
}
