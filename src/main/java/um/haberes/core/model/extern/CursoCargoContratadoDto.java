package um.haberes.core.model.extern;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CursoCargoContratadoDto {

    private Long cursoCargoContratadoId = null;

    private Long cursoId = null;

    private int anho = 0;

    private int mes = 0;

    private Long contratoId = null;

    private BigDecimal personaId = null;

    private Integer documentoId = null;

    private Integer cargoTipoId = null;

    private BigDecimal horasSemanales = BigDecimal.ZERO;

    private BigDecimal horasTotales = BigDecimal.ZERO;

    private Integer designacionTipoId = null;

    private Integer categoriaId = null;

    private Long cursoCargoNovedadId = null;

    private Byte acreditado = 0;

    private ContratadoPersonaDto contratadoPersona = null;
}
