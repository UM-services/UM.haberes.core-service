package um.haberes.core.model.view;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "vw_legajo_curso_cantidad")
@Immutable
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LegajoCursoCantidad {

    @Id
    private String uniqueId = null;

    private Integer anho = null;

    private Integer mes = null;

    private Long legajoId = null;

    private Integer facultadId = null;

    private Integer geograficaId = null;

    private Integer anuales = null;

    private Integer semestre1 = null;

    private Integer semestre2 = null;
}
