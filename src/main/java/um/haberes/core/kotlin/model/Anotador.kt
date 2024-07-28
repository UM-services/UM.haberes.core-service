package um.haberes.core.kotlin.model

import jakarta.persistence.*

@Entity
@Table
data class Anotador(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var anotadorId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var facultadId: Int? = null,
    var anotacion: String? = null,
    var visado: Byte = 0,
    var ipVisado: String? = null,
    var user: String? = null,
    var respuesta: String? = null,
    var autorizado: Byte = 0,
    var rechazado: Byte = 0,
    var rectorado: Byte = 0,
    var transferido: Byte = 0,

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    var facultad: Facultad? = null

) : Auditable()
