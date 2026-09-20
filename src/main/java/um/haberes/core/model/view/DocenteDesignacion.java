package um.haberes.core.model.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Table(name = "vw_docente_designacion")
@Immutable
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DocenteDesignacion {

    @Id
    private Long legajoId = null;

    private Integer anho = null;

    private Integer mes = null;

    private Integer facultadId = null;

    private Integer geograficaId = null;

    private String espacio = null;

    private BigDecimal horasSemanales = null;

    private String cargo = null;

    private String designacion = null;

    private BigDecimal horasDesignacion = null;

    private Byte anual = null;

    private Byte semestre1 = null;

    private Byte semestre2 = null;
}
