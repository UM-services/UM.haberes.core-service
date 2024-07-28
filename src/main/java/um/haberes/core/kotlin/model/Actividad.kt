package um.haberes.core.kotlin.model

import jakarta.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["legajoId", "anho", "mes"])])
data class Actividad(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var actividadId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var docente: Byte = 0,
    var otras: Byte = 0,
    var clases: Byte = 0,
    var dependenciaId: Int? = null,

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    var dependencia: Dependencia? = null

) : Auditable()
