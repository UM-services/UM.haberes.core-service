package um.haberes.core.hexagonal.liquidaciones.cargo.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.util.Jsonifyable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cargo implements Jsonifyable {

    private Long cargoId;

    private Long legajoId;

    private OffsetDateTime fechaAlta;

    private OffsetDateTime fechaBaja;

    private Integer dependenciaId;

    private Integer categoriaId;

    @Builder.Default
    private int jornada = 0;

    @Builder.Default
    private int presentismo = 0;

    @Builder.Default
    private BigDecimal horasJornada = BigDecimal.ZERO;

    private Persona persona;

    private Dependencia dependencia;

    private Categoria categoria;

}
