package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table
data class CargoClasePeriodo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cargoClasePeriodoId: Long? = null,

    var legajoId: Long? = null,
    var cargoClaseId: Long? = null,
    var dependenciaId: Int? = null,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var periodoDesde: Long? = null,
    var periodoHasta: Long? = null,
    var horas: Int = 0,
    var valorHora: BigDecimal = BigDecimal.ZERO,
    var descripcion: String? = null,

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
    var geografica: Geografica? = null

) : Auditable()
