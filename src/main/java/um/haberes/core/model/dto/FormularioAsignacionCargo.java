package um.haberes.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FormularioAsignacionCargo {

    private List<Categoria> categorias = null;

    private List<Categoria> categoriasAsignables = null;

    private List<Dependencia> dependencias = null;

    private List<Facultad> facultades = null;
}
