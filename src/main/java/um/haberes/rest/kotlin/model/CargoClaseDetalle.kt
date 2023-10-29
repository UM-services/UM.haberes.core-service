package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["cargoClasePeriodoId", "anho", "mes"])])
data class CargoClaseDetalle(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cargoClaseDetalleId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int? = 0,
    var mes: Int? = 0,
    var cargoClaseId: Long? = null,
    var dependenciaId: Int? = null,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var horas: Int = 0,
    var valorHora: BigDecimal = BigDecimal.ZERO,
    var cargoClasePeriodoId: Long? = null,
    var liquidado: Byte = 0,

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne
    @JoinColumn(name = "cargoClaseId", insertable = false, updatable = false)
    var cargoClase: CargoClase? = null,

    @OneToOne
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    var dependencia: Dependencia? = null,

    @OneToOne
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    var facultad: Facultad? = null,

    @OneToOne
    @JoinColumn(name = "geograficaId", insertable = false, updatable = false)
    var geografica: Geografica? = null,

    @OneToOne
    @JoinColumn(name = "cargoClasePeriodoId", insertable = false, updatable = false)
    var cargoClasePeriodo: CargoClasePeriodo? = null


) : Auditable()
