package um.haberes.core.hexagonal.cursos.curso.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoRequest {

    @NotBlank
    private String nombre;

    @NotNull
    private Integer facultadId;

    @NotNull
    private Integer geograficaId;

    private Byte anual;

    private Byte semestre1;

    private Byte semestre2;

    private Integer nivelId;

    private Byte adicionalCargaHoraria;
}
