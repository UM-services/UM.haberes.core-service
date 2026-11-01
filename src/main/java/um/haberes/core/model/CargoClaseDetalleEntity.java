package um.haberes.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.entity.FacultadEntity;
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.entity.GeograficaEntity;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.persistence.entity.DependenciaEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;
import um.haberes.core.util.Jsonifyable;

import java.math.BigDecimal;

@Entity
@Table(name = "cargo_clase_detalle", uniqueConstraints = {@UniqueConstraint(columnNames = {"cargoClasePeriodoId", "anho", "mes"})})
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CargoClaseDetalleEntity extends AuditableEntity implements Jsonifyable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cargoClaseDetalleId = null;

    private Long legajoId = null;

    private Integer anho = 0;

    private Integer mes = 0;

    private Long cargoClaseId = null;

    private Integer dependenciaId = null;

    private Integer facultadId = null;

    private Integer geograficaId = null;

    private int horas = 0;

    private BigDecimal valorHora = BigDecimal.ZERO;

    private Byte aplicaAdicional = 0;

    private Long cargoClasePeriodoId = null;

    private Byte liquidado = 0;

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    private PersonaEntity persona = null;

    @OneToOne
    @JoinColumn(name = "cargoClaseId", insertable = false, updatable = false)
    private CargoClaseEntity cargoClase = null;

    @OneToOne
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    private DependenciaEntity dependencia = null;

    @OneToOne
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    private FacultadEntity facultad = null;

    @OneToOne
    @JoinColumn(name = "geograficaId", insertable = false, updatable = false)
    private GeograficaEntity geografica = null;

    @OneToOne
    @JoinColumn(name = "cargoClasePeriodoId", insertable = false, updatable = false)
    private CargoClasePeriodoEntity cargoClasePeriodo = null;
}
