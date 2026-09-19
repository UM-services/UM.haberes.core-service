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
public class ContratadoPersonaDto {

    private String uniqueId = null;

    private BigDecimal personaId = null;

    private Integer documentoId = null;

    private String apellido = null;

    private String nombre = null;

    private String cuit = null;
}
